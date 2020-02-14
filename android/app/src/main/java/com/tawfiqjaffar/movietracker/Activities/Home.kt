package com.tawfiqjaffar.movietracker.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tawfiqjaffar.movietracker.Adapters.HomePagerAdapter
import com.tawfiqjaffar.movietracker.R
import kotlinx.android.synthetic.main.activity_home.*

class Home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setupViews()
    }

    private fun setupViews() {
        val fragAdapter = HomePagerAdapter(supportFragmentManager, this)
        viewPager.adapter = fragAdapter
        tabLayout.setupWithViewPager(viewPager)
    }
}
