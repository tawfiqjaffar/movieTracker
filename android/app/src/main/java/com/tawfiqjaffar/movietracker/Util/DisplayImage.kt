package com.tawfiqjaffar.movietracker.Util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.widget.ImageView
import java.lang.Exception
import java.net.URL

class DisplayImage(private val imageView: ImageView) : AsyncTask<String, Void, Bitmap>() {
    override fun doInBackground(vararg p0: String?): Bitmap? {
        val url = p0[0]
        var bmp: Bitmap? = null
        try {
            val inp = URL(url).openStream()
            bmp = BitmapFactory.decodeStream(inp)
        } catch (e: Exception) {
            Log.e("Error", e.message)
            e.printStackTrace()
        }
        return bmp
    }

    override fun onPostExecute(result: Bitmap?) {
        this.imageView.setImageBitmap(result)
    }

    fun getImagePath(posterPath: String): String {
        return ("https://image.tmdb.org/t/p/original${posterPath}")
    }

}