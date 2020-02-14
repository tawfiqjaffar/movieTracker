package com.tawfiqjaffar.movietracker.Activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.tawfiqjaffar.movietracker.R
import com.tawfiqjaffar.movietracker.Util.Api
import com.tawfiqjaffar.movietracker.Util.PreferencesHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val createAccount: RelativeLayout by lazy { findViewById<RelativeLayout>(R.id.createAccount) }
    private val TAG = "TJ_Login"
    private val context: Context by lazy { this }
    private val preferencesHelper : PreferencesHelper by lazy { PreferencesHelper(this.context) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setupViews()
    }

    override fun onResume() {
        super.onResume()
        preferencesHelper.getUser()?.let { user ->
            Log.d(TAG, user)
            preferencesHelper.getPassword()?.let {pass ->
                Log.d(TAG, pass)

                if (user.isNotEmpty() && pass.isNotEmpty()) {
                    remember.isChecked = true
                    username.isEnabled = false
                    username.isFocusable = false
                    password.isEnabled = false
                    password.isFocusable = false
                    loginUser(user, pass)
                }
            }
        }
    }

    private fun setupViews() {
        createAccount.setOnClickListener {
            startActivity(Intent(this.context, CreateAccount::class.java))
        }

        this.login.setOnClickListener {
            val username = this.username.text.toString()
            val password = this.password.text.toString()
            loginUser(username, password)
        }
    }

    private fun loginUser(username: String, password: String) {
        loading.visibility = View.VISIBLE

        val api = Api("http://192.168.1.11:8080", this.context)
        api.postRequest(
            "/api/users/login",
            { s ->
                loading.visibility = View.GONE
                preferencesHelper.setUser(username)
                if (remember.isChecked){
                    preferencesHelper.setPassword(password)
                }
                Log.d(TAG, s)
                startActivity(Intent(this.context, Home::class.java))
                finish()
            },
            {
                Toast.makeText(this.context, "Username or password is wrong", LENGTH_LONG).show()
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
