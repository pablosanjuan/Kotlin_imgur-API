package com.pabloSj.sofiapp.ui.viewActivity

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import com.pabloSj.sofiapp.R
import kotlinx.android.synthetic.main.activity_view.*

class ViewActivity : AppCompatActivity() {
    private var txtView: String? = ""
    private var imgView: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT
        supportActionBar?.let {
            it.title = ""
            it.setDisplayShowHomeEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)
        }

        if (savedInstanceState!=null){
            txtView = savedInstanceState.getString("title")
            imgView = savedInstanceState.getString("image")
        }
        titleView.text = txtView
        Glide.with(this).load(resources.getDrawable(R.drawable.index)).into(imageView)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
