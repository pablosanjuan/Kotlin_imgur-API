package com.pabloSj.sofiapp.ui.main

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.pabloSj.sofiapp.R
import com.pabloSj.sofiapp.data.api.ApiClient
import com.pabloSj.sofiapp.data.api.CardApiResponse
import com.pabloSj.sofiapp.data.api.service.Service
import com.pabloSj.sofiapp.data.model.Card
import com.pabloSj.sofiapp.ui.adapters.CardAdapter
import com.pabloSj.sofiapp.utils.IMG_TYPE
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: CardAdapter
    lateinit var layoutManager: LinearLayoutManager
    private var mShimmerViewContainer: ShimmerFrameLayout? = null

    var page = 0

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container)
        setupRecyclerView()
        val searchParameter = "ta"
            supportActionBar?.let {
                it.title = "Do a search!"
            }
        doSearch(0, "ta", IMG_TYPE)
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
                    load_more_bar.visibility = View.VISIBLE
                    doSearch(page, "ta", IMG_TYPE)
                }
            }
        })
    }

    private fun doSearch(page: Int, category: String, type: String) {
        val client = ApiClient()
        val service = client.provideHttpClient().create(Service::class.java)
        val call: Call<CardApiResponse> = service.getSearch(page, category, type)
        val filterData = ArrayList<Card>()
        call.enqueue(object : Callback<CardApiResponse> {
            override fun onResponse(call: Call<CardApiResponse>, response: Response<CardApiResponse>) {
                if (response.body()!!.success == "true") {
                    load_more_bar.visibility = View.GONE
                    mShimmerViewContainer?.let {
                        it.stopShimmerAnimation()
                        it.visibility = View.GONE
                    }
                    val data = response.body()!!.data
                    if (data.size==0){
                        supportActionBar?.let {
                            it.title = "0 results"
                        }
                    }
                    /**@NOTE: filterData: i had to filter data to avoid error 404 from API - bug in API*/
                    for (i in 0..data.size - 1) {
                        if (data[i].type != null) {
                            filterData.add(data[i])
                        }
                    }
                    adapter = CardAdapter(filterData)
                    rv.adapter = adapter
                } else {
                    Log.d("e", "Error Response")
                }
            }

            override fun onFailure(call: Call<CardApiResponse>, t: Throwable) {
                Log.d("error", t.message)
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
                doSearch(page,p0!!, IMG_TYPE)
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
}