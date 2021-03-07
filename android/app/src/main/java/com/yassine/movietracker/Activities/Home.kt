package com.yassine.movietracker.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.yassine.movietracker.Adapters.HomePagerAdapter
import com.yassine.movietracker.R
import com.yassine.movietracker.Util.PreferencesHelper
import kotlinx.android.synthetic.main.activity_home.*

class Home : AppCompatActivity() {

    private val TAG = "TJ_Home"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        Log.d(TAG, PreferencesHelper(this).getId())
        setupViews()
    }

    private fun setupViews() {
        val fragAdapter = HomePagerAdapter(supportFragmentManager, this)
        viewPager.adapter = fragAdapter
        tabLayout.setupWithViewPager(viewPager)
        setupTabLayout()
    }

    private fun setupTabLayout() {
        tabLayout.getTabAt(0)?.setIcon(R.drawable.ic_movies)
        tabLayout.getTabAt(1)?.setIcon(R.drawable.ic_fav)
        tabLayout.getTabAt(2)?.setIcon(R.drawable.ic_settings)
    }
}
