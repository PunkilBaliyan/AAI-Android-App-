package com.punkil.airportauthorityofindia

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ImportantLinksAdapter(private val importantLinks: List<String>) :
    RecyclerView.Adapter<ImportantLinksAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_important_link, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val linkTitle = importantLinks[position]
        holder.linkTitleTextView.text = linkTitle
    }

    override fun getItemCount(): Int {
        return importantLinks.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val linkTitleTextView: TextView = itemView.findViewById(R.id.linkTitleTextView)
    }
}
