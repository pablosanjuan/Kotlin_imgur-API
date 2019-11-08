package com.pabloSj.sofiapp.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pabloSj.sofiapp.R
import com.pabloSj.sofiapp.data.model.Card
import com.pabloSj.sofiapp.ui.viewActivity.ViewActivity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_view.*
import java.lang.Exception

class CardAdapter(ListSearch: List<Card>) : RecyclerView.Adapter<CardAdapter.ViewHolder>(){

    var cardItem=ListSearch
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return cardItem.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //holder.bindItem(cardItem[position])
        holder.bindItem(cardItem[position])
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val context = view.context
        fun bindItem(data: Card) {
            val title: TextView = itemView.findViewById(R.id.txtTitle)
            val image: ImageView = itemView.findViewById(R.id.image)
            title.text = data.title
            //Glide.with(itemView.context).load(data.link).into(image)
            Picasso.get()
                .load(data.link)
                //.resize(image.width,image.height)
                .into(image,object: Callback {
                    override fun onSuccess() {
                    }

                    override fun onError(e: Exception?) {
                        Picasso.get().load(R.drawable.index).into(image)
                    }
                })
            //Picasso.with(this).load(R.drawable.image).into(imageView);

            itemView.setOnClickListener {
                //Toast.makeText(itemView.context, "click ${data.name}", Toast.LENGTH_SHORT).show()
                val intent = Intent(context, ViewActivity::class.java)
                intent.putExtra("title", data.title)
                intent.putExtra("image", data.link)
                context.startActivity(intent)
            }
        }
    }
}