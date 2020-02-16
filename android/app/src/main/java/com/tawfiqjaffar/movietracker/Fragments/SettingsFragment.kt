package com.tawfiqjaffar.movietracker.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.tawfiqjaffar.movietracker.Activities.MainActivity
import com.tawfiqjaffar.movietracker.R
import com.tawfiqjaffar.movietracker.Util.PreferencesHelper

class SettingsFragment : Fragment() {
    private lateinit var disconnect: RelativeLayout
    private lateinit var username: TextView

    companion object {
        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.settings_fragment, container, false)
        setupViews(view)
        return view
    }

    private fun setupViews(view: View) {
        activity?.let {activity ->
            context?.let {context ->
                val preferencesHelper = PreferencesHelper(context)
                this.disconnect = view.findViewById(R.id.disconnect)
                this.disconnect.setOnClickListener {
                    preferencesHelper.setUser("")
                    preferencesHelper.setPassword("")
                    preferencesHelper.setId("")
                    activity.startActivity(Intent(context, MainActivity::class.java))
                    activity.finish()
                }
                this.username = view.findViewById(R.id.username)
                this.username.text = preferencesHelper.getUser()
            }
        }
    }
}