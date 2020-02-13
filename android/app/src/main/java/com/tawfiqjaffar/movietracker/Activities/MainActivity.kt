package com.tawfiqjaffar.movietracker.Activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import com.tawfiqjaffar.movietracker.R
import com.tawfiqjaffar.movietracker.Util.Api
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val createAccount: RelativeLayout by lazy { findViewById<RelativeLayout>(R.id.createAccount) }
    private val TAG = "TJ_Login"
    private val context: Context by lazy { this }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setupViews()
    }

    private fun setupViews() {
        createAccount.setOnClickListener {
            startActivity(Intent(this.context, CreateAccount::class.java))
        }
        this.login.setOnClickListener {
            loading.visibility = View.VISIBLE
            val username = this.username.text.toString()
            val password = this.password.text.toString()
            val api = Api("http://192.168.1.11:8080", this.context)
            api.getRequest(
                "/api/users/all",
                { s ->
                    loading.visibility = View.GONE
                    Log.d(TAG, s)
                },
                {
                    loading.visibility = View.GONE
                    Log.e(TAG, "Something went wrong")
                },
                hashMapOf(
                    Pair("username", username),
                    Pair("password", password)
                )
            )
        }
    }
}
