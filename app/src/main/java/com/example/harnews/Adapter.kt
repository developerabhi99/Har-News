package com.example.harnews

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class Adapter(val userList: List<newsdata>,private val handleclick: handleclick):RecyclerView.Adapter<Adapter.pViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): pViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.news,parent,false)
        val view =pViewHolder(itemView)
        itemView.setOnClickListener {
            handleclick.onItemClicked(userList[view.adapterPosition])
        }
        return view
    }

    override fun onBindViewHolder(holder: pViewHolder, position: Int) {
        val currentitem=userList[position]
        holder.newsdesciption.text=currentitem.description
        holder.title.text=currentitem.title
        holder.date.text=currentitem.publishedAt
        Glide.with(holder.itemView.context).load(currentitem.urlToImage).into(holder.newsimage)
        holder.share.setOnClickListener { 
            shareoption(holder.itemView.context,currentitem.url)
        }



    }

    private fun shareoption(url1: Context, url: String?) {
        val intents= Intent(Intent.ACTION_SEND)


        //if no picture, just text set - this MIME
        intents.putExtra(Intent.EXTRA_TEXT,"Hey check this News FROM Harnews APP Developed by Abhijit Shaw ${url}")
        intents.setType("text/plain")




        val chooser= Intent.createChooser(intents,"Great App")

        try {
        url1.startActivity(chooser)
        } catch (e: ActivityNotFoundException) {
            // Define what your app should do if no activity can handle the intent.
        }
    }

    override fun getItemCount(): Int {


        return userList.size
    }
    class pViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val newsimage:ImageView =itemView.findViewById(R.id.newsimage)
        val newsdesciption:TextView = itemView.findViewById(R.id.newsdescription)
        val title:TextView=itemView.findViewById(R.id.newstitle)
        val date:TextView=itemView.findViewById(R.id.newsdate)
        val share:ImageButton=itemView.findViewById(R.id.newsshare)

    }
}
interface handleclick{
    fun onItemClicked(item: newsdata)
}