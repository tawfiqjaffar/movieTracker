package com.yassine.movietracker.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import com.yassine.movietracker.Adapters.DiscoverAdapter
import com.yassine.movietracker.Models.DiscoverResults
import com.yassine.movietracker.Models.Movie
import com.yassine.movietracker.R
import com.yassine.movietracker.Util.Api
import java.io.StringReader


class DiscoverFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private val dataSet: ArrayList<Movie> = ArrayList()
    private lateinit var adatper : DiscoverAdapter
    private lateinit var loading: ProgressBar
    private val TAG = "TJ_DiscoverFragment"

    companion object {
        open fun newInstance(): DiscoverFragment {
            val frag = DiscoverFragment()
            return frag
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.discover_fragment, container, false)

        setupViews(view)
        return view
    }


    override fun onResume() {
        super.onResume()
        getMovies()

    }

    private fun getMovies() {
        this.loading.visibility = View.VISIBLE
        context?.let {
            val api = Api(baseUrl = "https://api.themoviedb.org/3", context = it)
            api.getRequest("/discover/movie?api_key=04b7cef2685f0872e49f0b440ea7ce13",
                { s ->
                    this.loading.visibility = View.GONE
                    Log.d(TAG, s)
                    val gson = Gson()
                    val reader = JsonReader(StringReader(s))
                    reader.isLenient = true
                    val dr = gson.fromJson<DiscoverResults>(reader, DiscoverResults::class.java)
                    dataSet.clear()
                    dataSet.addAll(dr.results)
                    this.adatper.notifyDataSetChanged()
                }, {
                    this.loading.visibility = View.GONE
                    Log.e(TAG, "Something went wrong")
                    Toast.makeText(it, "Somethin went wrong", Toast.LENGTH_LONG).show()
                }
            )
        }
    }

    private fun setupViews(view: View?) {
        context?.let {
            view?.let { v ->
                this.recyclerView = v.findViewById(R.id.recycler)
                this.adatper = DiscoverAdapter(dataSet, it)
                this.recyclerView.adapter = adatper
                this.recyclerView.layoutManager = LinearLayoutManager(it)
                this.loading = v.findViewById(R.id.loading)
            }
        }
    }

}