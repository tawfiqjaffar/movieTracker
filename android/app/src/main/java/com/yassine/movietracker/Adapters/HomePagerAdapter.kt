package com.yassine.movietracker.Adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.yassine.movietracker.Fragments.DiscoverFragment
import com.yassine.movietracker.Fragments.FavoritesFragment
import com.yassine.movietracker.Fragments.SettingsFragment

class HomePagerAdapter(private val fragmentManager: FragmentManager, private val context: Context) :
    FragmentPagerAdapter(fragmentManager) {
    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        return (when (position) {
            0 -> DiscoverFragment.newInstance()
            1 -> FavoritesFragment.newInstance()
            else -> SettingsFragment.newInstance()
        })
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return (when (position) {
            0 -> "Discover"
            1 -> "Favorites"
            else -> "Settings"
        })
    }

}