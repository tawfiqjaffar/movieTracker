package com.tawfiqjaffar.movietracker.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tawfiqjaffar.movietracker.Models.Comment
import com.tawfiqjaffar.movietracker.R
import kotlinx.android.synthetic.main.comment_item.view.*

class CommentsAdapter(private val dataSet: List<Comment>, private val context: Context) :
        RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val content : TextView = view.content
        val user : TextView = view.user
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.comment_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = this.dataSet[position]
        holder.content.text = item.content
        holder.user.text = item.name
    }

    override fun getItemCount(): Int {
        return this.dataSet.size
    }
}