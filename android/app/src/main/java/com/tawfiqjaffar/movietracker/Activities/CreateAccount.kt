package com.tawfiqjaffar.movietracker.Activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RelativeLayout
import com.tawfiqjaffar.movietracker.R
import com.tawfiqjaffar.movietracker.Util.Api
import kotlinx.android.synthetic.main.activity_create_account.createAccount
import kotlinx.android.synthetic.main.activity_create_account.password
import kotlinx.android.synthetic.main.activity_create_account.username

class CreateAccount : AppCompatActivity() {


    private val logIn : RelativeLayout by lazy { findViewById<RelativeLayout>(R.id.logIn) }
    private val context: Context by lazy { this }
    private val TAG = "TJ_CreateAccount"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        setupViews()
    }

    private fun setupViews() {
        this.createAccount.setOnClickListener {
            val username = this.username.text.toString()
            val password = this.password.text.toString()
            val api = Api("http://192.168.1.11:8080", this.context)
            api.postRequest(
                "/api/users/register",
                { s ->
                    Log.d(TAG, s)
                },
                {
                    Log.e(TAG, "Something went wrong")
                },
                hashMapOf(
                    Pair("username", username),
                    Pair("password", password)
                )
            )
        }
        this.logIn.setOnClickListener {
            finish()
        }
    }
}
