package com.pabloSj.sofiapp.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pabloSj.sofiapp.R
import com.pabloSj.sofiapp.ui.viewActivity.ViewActivity

class AdapterList(var ListSearch: List<ListSearch>) : RecyclerView.Adapter<AdapterList.ViewHolder>(){

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return ListSearch.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(ListSearch[position])
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val context = view.context
        fun bindItem(data: ListSearch) {
            val title: TextView = itemView.findViewById(R.id.txtTitle)
            val image: ImageView = itemView.findViewById(R.id.image)
            title.text = data.name
            Glide.with(itemView.context).load(data.image).into(image)

            itemView.setOnClickListener {
                //Toast.makeText(itemView.context, "click ${data.name}", Toast.LENGTH_SHORT).show()
                val intent = Intent(context, ViewActivity::class.java)
                intent.putExtra("title", data.name)
                intent.putExtra("image", data.image)
                context.startActivity(intent)
            }
        }
    }
}