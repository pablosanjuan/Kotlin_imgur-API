package com.pabloSj.sofiapp.ui.viewActivity

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import com.pabloSj.sofiapp.R
import com.pabloSj.sofiapp.utils.IMAGE_CARD
import com.pabloSj.sofiapp.utils.TITLE_CARD
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_view.*
import java.lang.Exception

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

        if (savedInstanceState == null) {
            val extras = intent.extras
            if (extras != null) {
                txtView = extras.getString(TITLE_CARD)
                imgView = extras.getString(IMAGE_CARD)
            }
        }
        titleView.text = txtView
        Picasso.get()
            .load(imgView)
            .into(imageView,object: Callback{
                override fun onSuccess() {
                }

                override fun onError(e: Exception?) {
                    Picasso.get().load(R.drawable.error_image).into(imageView)
                }
            })
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