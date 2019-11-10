package com.pabloSj.sofiapp.ui.main

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.pabloSj.sofiapp.R
import com.pabloSj.sofiapp.data.model.Card
import com.pabloSj.sofiapp.data.model.Status
import com.pabloSj.sofiapp.ui.adapters.CardAdapter
import com.pabloSj.sofiapp.ui.base.BaseActivity
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_alert_dialog.view.*
import javax.inject.Inject

class MainActivity : BaseActivity<com.pabloSj.sofiapp.databinding.ActivityMainBinding, MainActivityViewModel>(),
    HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    private lateinit var adapter: CardAdapter
    lateinit var layoutManager: LinearLayoutManager
    private var mShimmerViewContainer: ShimmerFrameLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAndBindContentView(R.layout.activity_main)
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container)
        viewModel.page.value = 1
        viewModel.searchParameter.value = "a"
        supportActionBar?.let {
            it.title = "Do a search"
        }
        viewModel.search(viewModel.searchParameter.value!!)
        observeViewModel()
        setupRecyclerView()
    }

    private fun observeViewModel() {
        viewModel.searching.observe(this, Observer {
            val filterData = ArrayList<Card>()
            if (it?.status == Status.SUCCESS) {
                val data = it.data?.data
                if (data?.size == 0) {
                    supportActionBar?.let {
                        it.title = "0 results, do a new search"
                    }
                }
                /**@NOTE: filterData: i had to filter data to avoid error 404 from API - bug in API*/
                for (i in 0 until data!!.size) {
                    if (data?.get(i)?.type != null) {
                        filterData.add(data[i])
                    }
                }
                handleResponseEvent(filterData)
            } else {
                Log.d("e", "Error Response")
            }
        })
    }

    private fun handleResponseEvent(filterData: ArrayList<Card>) {
        load_more_bar.visibility = View.GONE
        mShimmerViewContainer?.let { it ->
            it.stopShimmerAnimation()
            it.visibility = View.GONE
        }
        if (viewModel.page.value == 1) {
            adapter = CardAdapter(filterData)
            rv.adapter = adapter
        } else
            filterData.let {
                viewModel.cachedList.value = filterData
                adapter = CardAdapter(filterData)
                rv.adapter = adapter
            }
    }

    @SuppressLint("WrongConstant")
    private fun setupRecyclerView() {
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val listCards = ArrayList<Card>()
        adapter = CardAdapter(listCards)
        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                layoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (layoutManager.findLastCompletelyVisibleItemPosition() == adapter.itemCount - 1) {
                    load_more_bar.visibility = View.VISIBLE
                    viewModel.loadNextPage(viewModel.searchParameter.value!!)
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflate = menuInflater
        inflate.inflate(R.menu.menu_search, menu)
        val manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchIcon = menu?.findItem(R.id.search)
        val viewSearch = searchIcon?.actionView as SearchView
        viewSearch.setSearchableInfo(manager.getSearchableInfo(componentName))
        viewSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                viewSearch.clearFocus()
                viewSearch.setQuery("", false)
                searchIcon.collapseActionView()
                mShimmerViewContainer?.let {
                    it.startShimmerAnimation()
                    it.visibility = View.VISIBLE
                }
                supportActionBar?.let {
                    it.title = "Search: $p0"
                }
                viewModel.searchParameter.value = p0
                viewModel.page.value = 1
                viewModel.search(viewModel.searchParameter.value!!)
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
        return true
    }

    public override fun onResume() {
        super.onResume()
        mShimmerViewContainer?.startShimmerAnimation()
    }

    override fun onPause() {
        mShimmerViewContainer?.stopShimmerAnimation()
        super.onPause()
    }

    override fun onBackPressed() {
        showConfirmationDialog()
    }

    @SuppressLint("InflateParams")
    private fun showConfirmationDialog() {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_alert_dialog, null)
        val mBuilder = android.app.AlertDialog.Builder(this)
            .setView(mDialogView)
            .setCancelable(false)
        val mAlertDialog = mBuilder.show()
        mDialogView.acceptButton.setOnClickListener {
            finish()
        }
        mDialogView.cancelButton.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingAndroidInjector
}