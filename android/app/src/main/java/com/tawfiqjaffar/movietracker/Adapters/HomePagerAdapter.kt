package com.tawfiqjaffar.movietracker.Adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.tawfiqjaffar.movietracker.Fragments.DiscoverFragment

class HomePagerAdapter(private val fragmentManager: FragmentManager, private val context: Context) : FragmentPagerAdapter(fragmentManager) {
    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        return DiscoverFragment.newInstance()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return "Discover"
    }

}