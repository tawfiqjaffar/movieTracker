package com.tawfiqjaffar.movietracker.Fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.tawfiqjaffar.movietracker.Adapters.DiscoverAdapter
import com.tawfiqjaffar.movietracker.Models.LoginResponse
import com.tawfiqjaffar.movietracker.Models.Movie
import com.tawfiqjaffar.movietracker.R
import com.tawfiqjaffar.movietracker.Util.Api
import com.tawfiqjaffar.movietracker.Util.PreferencesHelper

class FavoritesFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private val dataSet: ArrayList<Movie> = ArrayList()
    private lateinit var adatper: DiscoverAdapter
    private lateinit var loading: ProgressBar
    private val dataSetFav = arrayListOf<Int>()
    private val TAG = "TJ_FavoritesFragment"

    companion object {
        fun newInstance() = FavoritesFragment()
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

    private fun setupViews(view: View?) {
        this.context?.let { context ->
            view?.let { view ->
                this.recyclerView = view.findViewById(R.id.recycler)
                this.adatper = DiscoverAdapter(dataSet, context)
                this.recyclerView.adapter = this.adatper
                this.recyclerView.layoutManager = LinearLayoutManager(context)
                this.loading = view.findViewById(R.id.loading)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        this.dataSet.clear()
        this.dataSetFav.clear()
        this.recyclerView.adapter?.notifyDataSetChanged()
        getFav()
    }

    private fun getFav() {
        this.context?.let { context ->
            val id = PreferencesHelper(context).getId()

            id?.let { id ->
                val api = Api("http://10.41.165.65:8080", context)
                api.getRequest("/api/users/find",
                    { s ->
                        val resp = Gson().fromJson<LoginResponse>(s, LoginResponse::class.java)
                        resp.data.user.favorites.forEach {
                            this.dataSetFav.add(it.toInt())
                            Log.d(TAG, it)
                        }
                        getMovies()
                    }, {
                        Log.d(TAG, "Something went wrong")
                    },
                    hashMapOf(Pair("id", id))
                )
            }
        }

    }

    private fun getMovies() {
        this.context?.let { context ->
            run {
                val api = Api("https://api.themoviedb.org/3", context)
                this.dataSetFav.forEach { movieId ->
                    api.getRequest("/movie/${movieId}?api_key=04b7cef2685f0872e49f0b440ea7ce13",
                        { s ->
                            run {
                                val movie = Gson().fromJson<Movie>(s, Movie::class.java)
                                this.dataSet.add(movie)
                                Log.d(TAG, s)
                                this.recyclerView.adapter?.notifyItemInserted(this.dataSet.size - 1)
                            }
                        }, {
                            Log.e(TAG, "Something went wrong")
                        })
                }
            }
        }
    }
}