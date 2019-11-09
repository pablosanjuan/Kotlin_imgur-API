package com.pabloSj.sofiapp.ui.main

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
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
import com.pabloSj.sofiapp.utils.IMG_TYPE
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity<com.pabloSj.sofiapp.databinding.ActivityMainBinding, MainActivityViewModel>(),
    HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    private lateinit var adapter: CardAdapter
    lateinit var layoutManager: LinearLayoutManager
    private var mShimmerViewContainer: ShimmerFrameLayout? = null
    var page = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAndBindContentView(R.layout.activity_main)
        viewModel.search("pablo")
        observeViewModel()
        setupRecyclerView()
    }

    private fun observeViewModel() {
        viewModel.searching.observe(this, Observer {
            val filterData = ArrayList<Card>()
            if (it?.status == Status.SUCCESS) {
                mShimmerViewContainer?.let { it ->
                    it.stopShimmerAnimation()
                    it.visibility = View.GONE
                }
                val data = it.data?.data
                if (data?.size == 0) {
                    supportActionBar?.let {
                        it.title = "0 results"
                    }
                }
                /**@NOTE: filterData: i had to filter data to avoid error 404 from API - bug in API*/
                for (i in 0 until data!!.size) {
                    if (data?.get(i)?.type != null) {
                        filterData.add(data[i])
                    }
                }
                handleResponseEvent(filterData)
                adapter = CardAdapter(filterData)
                rv.adapter = adapter
            } else {
                Log.d("e", "Error Response")
            }
//        val ss = ""
//            if (response.body()!!.success == "true") {
            //                    load_more_bar.visibility = View.GONE
//                    mShimmerViewContainer?.let {
//                        it.stopShimmerAnimation()
//                        it.visibility = View.GONE
//                    }
//                    val data = response.body()!!.data
//                    if (data.size==0){
//                        supportActionBar?.let {
//                            it.title = "0 results"
//                        }
//                    }
//                    /**@NOTE: filterData: i had to filter data to avoid error 404 from API - bug in API*/
//                    for (i in 0..data.size - 1) {
//                        if (data[i].type != null) {
//                            filterData.add(data[i])
//                        }
//                    }
//                    c
            //handleResponseEvent(it)
        })
    }

    private fun handleResponseEvent(filterData: ArrayList<Card>) {

        if (viewModel.page == 1) {
            viewModel.cachedJobsResultsList.value = filterData
        } else
            filterData.let {
                viewModel.cachedJobsResultsList.value = adapter.getCurrentList().union(it).toList()
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
                    page += 1
                    //load_more_bar.visibility = View.VISIBLE
                    //doSearch(page, "ta", IMG_TYPE)
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

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingAndroidInjector
}