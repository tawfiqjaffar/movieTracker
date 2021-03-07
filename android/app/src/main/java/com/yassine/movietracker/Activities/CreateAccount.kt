package com.yassine.movietracker.Activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import com.yassine.movietracker.R
import com.yassine.movietracker.Util.Api
import com.yassine.movietracker.Util.PreferencesHelper
import kotlinx.android.synthetic.main.activity_create_account.*

class CreateAccount : AppCompatActivity() {


    private val logIn: RelativeLayout by lazy { findViewById<RelativeLayout>(R.id.logIn) }
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
            val confirm = this.confirmPassword.text.toString()
            val password = this.password.text.toString()
            if (confirm.isNotEmpty() && password.isNotEmpty() && username.isNotEmpty() && confirm == password)
                createAccount(username, password)
            else
                Toast.makeText(context, "Passwords don't match", Toast.LENGTH_LONG).show()
        }
        this.logIn.setOnClickListener {
            finish()
        }
    }

    private fun createAccount(username: String, password: String) {
        loading.visibility = View.VISIBLE
        val api = Api(PreferencesHelper(this.context).getIP()!!, this.context)
        api.postRequest(
            "/api/users/register",
            { s ->
                loading.visibility = View.GONE
                finish()
                Log.d(TAG, s)
            },
            {
                loading.visibility = View.GONE
                if (it == 409) {
                    Toast.makeText(context, "Username already exists", Toast.LENGTH_LONG).show()
                } else {
                    Log.e(TAG, "Something went wrong $it")
                    Toast.makeText(context, "Something went wrong $it", Toast.LENGTH_LONG).show()
                }
            },
            hashMapOf(
                Pair("username", username),
                Pair("password", password)
            )
        )
    }
}
