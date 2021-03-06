package com.yassine.movietracker.Util

import android.content.Context
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class Api(private val baseUrl: String, private val context: Context) {
    fun getRequest(
        route: String,
        callback: (String) -> (Unit),
        errorCallback: () -> (Unit),
        headers: HashMap<String, String> = hashMapOf()
    ) {
        val url = "${baseUrl}${route}"
        val queue = Volley.newRequestQueue(this.context)
        val stringRequest = object : StringRequest(Method.GET, url,
            Response.Listener<String> { response ->
                callback(response) },
            Response.ErrorListener { errorCallback() }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                return headers
            }
        }
        queue.add(stringRequest)
    }

    fun postRequest(
        route: String,
        callback: (String) -> (Unit),
        errorCallback: (Int) -> (Unit),
        headers: HashMap<String, String> = hashMapOf()
    ) {
        val url = "${baseUrl}${route}"
        val queue = Volley.newRequestQueue(this.context)
        val stringRequest = object : StringRequest(Method.POST, url,
            Response.Listener<String> { response ->
                callback(response)
            },
            Response.ErrorListener { error ->
                Log.d("API", error.toString())
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                return headers
            }
        }
        queue.add(stringRequest)
    }

    fun putRequest(
        route: String,
        callback: (String) -> (Unit),
        errorCallback: (Int) -> (Unit),
        headers: HashMap<String, String> = hashMapOf()
    ) {
        val url = "${baseUrl}${route}"
        val queue = Volley.newRequestQueue(this.context)
        val stringRequest = object : StringRequest(Method.PUT, url,
            Response.Listener<String> { response -> callback(response) },
            Response.ErrorListener { error ->  }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                return headers
            }
        }
        queue.add(stringRequest)
    }
}