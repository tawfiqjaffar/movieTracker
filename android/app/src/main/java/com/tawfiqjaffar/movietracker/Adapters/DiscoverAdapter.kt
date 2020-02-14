package com.tawfiqjaffar.movietracker.Adapters

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tawfiqjaffar.movietracker.Models.Movie
import com.tawfiqjaffar.movietracker.R
import com.tawfiqjaffar.movietracker.Util.DisplayImage
import kotlinx.android.synthetic.main.movie_item.view.*

class DiscoverAdapter(private val dataSet: List<Movie>, private val context: Context) :
    RecyclerView.Adapter<DiscoverAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageview: ImageView = view.imageView
        val title: TextView = view.title
        val description: TextView = view.description
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.movie_item, parent, false)
        return ViewHolder((view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = this.dataSet[position]
        val di = DisplayImage(holder.imageview)
        di.execute(di.getImagePath(item.posterPath))
        holder.title.text = item.title
        holder.description.text = item.overview
    }

    override fun getItemCount() = this.dataSet.size
}