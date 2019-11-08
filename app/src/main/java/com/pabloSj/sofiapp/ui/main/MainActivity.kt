package com.pabloSj.sofiapp.ui.main

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.pabloSj.sofiapp.R
import com.pabloSj.sofiapp.ui.adapters.AdapterList
import com.pabloSj.sofiapp.ui.adapters.ListSearch
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL,false)
        val listSearch=ArrayList<ListSearch>()
        listSearch.add(ListSearch("pablo", R.drawable.index))
        listSearch.add(ListSearch("cesar", R.drawable.index))
        listSearch.add(ListSearch("andres", R.drawable.index))
        listSearch.add(ListSearch("felipe", R.drawable.index))

        val adapter = AdapterList(listSearch)
        rv.adapter = adapter
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
}
