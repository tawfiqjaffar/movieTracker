package com.yassine.movietracker.Activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.yassine.movietracker.R
import com.yassine.movietracker.Util.PreferencesHelper
import kotlinx.android.synthetic.main.activity_server.*

class Server : AppCompatActivity() {


    private val context: Context by lazy { this }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_server)

        setupViews()
    }

    private fun setupViews() {
        this.save.setOnClickListener {
            if (this.username.text.toString().isNotEmpty()) {
                val prefHelp = PreferencesHelper(this.context)
                prefHelp.setIP(this.username.text.toString())
                finish()
            } else {
                Toast.makeText(this.context, "IP address field must not be empty", LENGTH_LONG)
                    .show()
            }
        }
    }
}
