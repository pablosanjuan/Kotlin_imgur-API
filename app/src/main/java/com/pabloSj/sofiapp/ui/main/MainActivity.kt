package com.pabloSj.sofiapp.ui.main

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
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
    private var mShimmerViewContainer: ShimmerFrameLayout? = null

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container)
        val listCards = ArrayList<Card>()
        adapter = CardAdapter(listCards)
        rv.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
//        val listSearch=ArrayList<ListSearch>()
//        listSearch.add(ListSearch("pablo", R.drawable.index))
//        listSearch.add(ListSearch("cesar", R.drawable.index))
//        listSearch.add(ListSearch("andres", R.drawable.index))
//        listSearch.add(ListSearch("felipe", R.drawable.index))
        doSearch(0,"cats", IMG_TYPE)
    }

    private fun doSearch(page: Int, category:String, type:String) {
        val client = ApiClient()
        val service = client.provideHttpClient().create(Service::class.java)
        val call: Call<CardApiResponse> = service.getSearch(page, category, type)
        call.enqueue(object : Callback<CardApiResponse> {
            override fun onResponse(call: Call<CardApiResponse>, response: Response<CardApiResponse>) {
                if(response.body()!!.success=="true") {
                    val data = response.body()!!.data
                    mShimmerViewContainer?.let {
                        it.stopShimmerAnimation()
                        it.visibility = View.GONE
                    }
                    val adapter = CardAdapter(data)
                    rv.adapter = adapter
                }else{
                    Log.d("e","Error Response")
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
                Toast.makeText(this@MainActivity, p0, Toast.LENGTH_LONG).show()
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
