package com.yassine.movietracker.Activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.yassine.movietracker.Adapters.CommentsAdapter
import com.yassine.movietracker.Models.Comment
import com.yassine.movietracker.Models.CommentResponse
import com.yassine.movietracker.Models.FavResponse
import com.yassine.movietracker.Models.Movie
import com.yassine.movietracker.R
import com.yassine.movietracker.Util.Api
import com.yassine.movietracker.Util.DisplayImage
import com.yassine.movietracker.Util.PreferencesHelper
import kotlinx.android.synthetic.main.activity_full_movie.*

class FullMovie : AppCompatActivity() {

    private val context: Context by lazy { this }
    private val movie: Movie by lazy { intent.getSerializableExtra("movie") as Movie }
    private val prefHelp: PreferencesHelper by lazy { PreferencesHelper(context) }
    private val TAG = "TJ_FullMovie"
    private val dataSet = arrayListOf<Comment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_movie)

        setupViews()
    }

    override fun onResume() {
        super.onResume()
        getComments()
    }

    private fun getComments() {
        this.loading.visibility = View.VISIBLE
        val api = Api(PreferencesHelper(this.context).getIP()!!, this.context)
        api.getRequest("/api/comments/find", { s ->
            val response = Gson().fromJson<CommentResponse>(s, CommentResponse::class.java)
            this.dataSet.clear()
            this.dataSet.addAll(response.data.data)
            this.loading.visibility = View.GONE
            this.recycler.adapter?.notifyDataSetChanged()
        }, {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show()
            this.loading.visibility = View.GONE
        },
            hashMapOf(Pair("movieid", this.movie.id.toString()))
        )
    }

    private fun setupViews() {
        val di = DisplayImage(image)
        di.execute(di.getImagePath(movie.posterPath))
        titleview.text = movie.title
        description.text = movie.overview
        this.recycler.adapter = CommentsAdapter(this.dataSet, this.context)
        this.recycler.layoutManager = LinearLayoutManager(context)
        this.submit.setOnClickListener {
            val content = this.chatBox.text.toString()
            if (content.isNotEmpty()) {
                newComment(content)
                this.chatBox.text.clear()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_favorite -> {
            addRemoveFavorite { action ->
                if (action == "favorited") {
                    item.icon = ContextCompat.getDrawable(context, R.drawable.ic_favsmall)
                } else {
                    item.icon = ContextCompat.getDrawable(context, R.drawable.ic_unfav)
                }
            }
            true
        }
        else -> false
    }

    private fun newComment(content: String) {
        val prefHelp = PreferencesHelper(this.context)
        prefHelp.getId()?.let { id ->
            prefHelp.getUser()?.let { user ->


                this.loading.visibility = View.VISIBLE
                val api = Api(PreferencesHelper(this.context).getIP()!!, this.context)
                api.postRequest("/api/comments/new", { s ->
                    this.loading.visibility = View.GONE
                    getComments()
                }, {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show()
                    this.loading.visibility = View.GONE
                },
                    hashMapOf(
                        Pair("content", content),
                        Pair("movieId", this.movie.id.toString()),
                        Pair("id", id),
                        Pair("name", user)
                    )
                )
            }
        }
    }

    private fun addRemoveFavorite(changeIcon: (String) -> Unit) {
        loading.visibility = View.VISIBLE
        prefHelp.getId()?.let {
            val api = Api(PreferencesHelper(this.context).getIP()!!, context)
            api.putRequest("/api/users/favorite",
                { s ->
                    val resp = Gson().fromJson<FavResponse>(s, FavResponse::class.java)
                    val action = resp.data.action
                    changeIcon(action)
                    loading.visibility = View.GONE
                    Log.d(TAG, s)

                }, {
                    loading.visibility = View.GONE
                    Log.d(TAG, "Something went wrong")
                },
                hashMapOf(
                    Pair("id", it),
                    Pair("movieid", this.movie.id.toString())
                )
            )
        }
    }
}
