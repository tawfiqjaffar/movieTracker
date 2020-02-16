package com.tawfiqjaffar.movietracker.Util

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class PreferencesHelper(private val context: Context) {
    private val TAG = "MovieApp"
    val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences("${TAG}", Context.MODE_PRIVATE)
    }

    fun setUser(user: String) {
        with(this.sharedPreferences.edit()) {
            putString("${TAG}_USER", user)
            commit()
        }
    }

    fun getUser(): String? {
        return (this.sharedPreferences.getString("${TAG}_USER", ""))
    }

    fun setPassword(password: String) {
        with (this.sharedPreferences.edit()) {
            putString("${TAG}_PASSWORD", password)
            commit()
        }
    }

    fun getPassword() : String? {
        return (this.sharedPreferences.getString("${TAG}_PASSWORD", ""))
    }

    fun setId(id: String) {
        Log.d("TJ_SharedPref", id)
        with (this.sharedPreferences.edit()) {
            putString("${TAG}_ID", id)
            commit()
        }
    }

    fun getId() : String? {
        return (this.sharedPreferences.getString("${TAG}_ID", ""))
    }

    fun setIP(ip: String) {
        with(this.sharedPreferences.edit()) {
            putString("MEDIKIT_IP", ip)
            commit()
        }
    }

    fun getIP(): String? {
        return (this.sharedPreferences.getString("MEDIKIT_IP", "192.168.1.11"))
    }

    fun setPort(port: Int) {
        with(this.sharedPreferences.edit()) {
            putInt("MEDIKIT_PORT", port)
            commit()
        }
    }

    fun getPort(): Int {
        return (this.sharedPreferences.getInt("MEDIKIt_PORT", 8080))
    }
}