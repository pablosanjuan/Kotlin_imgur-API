package com.pabloSj.sofiapp.ui.adapters

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.pabloSj.sofiapp.R
import com.pabloSj.sofiapp.data.model.Card
import com.pabloSj.sofiapp.ui.viewActivity.ViewActivity
import com.pabloSj.sofiapp.utils.IMAGE_CARD
import com.pabloSj.sofiapp.utils.TITLE_CARD
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception
import java.util.Collections.addAll

class CardAdapter(ListSearch: List<Card>) : RecyclerView.Adapter<CardAdapter.ViewHolder>(){

    var cardItem=ListSearch
    lateinit var context: Context
    private var currentList: List<Card>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return cardItem.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(cardItem[position])
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val context = view.context
        fun bindItem(data: Card) {
            val title: TextView = itemView.findViewById(R.id.txtTitle)
            val image: ImageView = itemView.findViewById(R.id.image)
            title.text = data.title
            Picasso.get()
                .load(data.link)
                .into(image,object: Callback {
                    override fun onSuccess() {
                    }

                    override fun onError(e: Exception?) {
                        Picasso.get().load(R.drawable.error_image).into(image)
                    }
                })

            itemView.setOnClickListener {
                val options = ActivityOptions.makeSceneTransitionAnimation(context as Activity)
                val intent = Intent(context, ViewActivity::class.java)
                intent.putExtra(TITLE_CARD, data.title)
                intent.putExtra(IMAGE_CARD, data.link)
                context.startActivity(intent, options.toBundle())
            }
        }
    }

    fun getCurrentList(): List<Card> = currentList ?: emptyList()

    fun addData(listItems: ArrayList<Card>) {
        var size = listItems.size
        addAll(listItems)
        var sizeNew = listItems.size
        notifyItemRangeChanged(size, sizeNew)
    }
}