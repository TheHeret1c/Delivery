package com.example.delivery.main

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.delivery.R
import com.example.delivery.databinding.FragmentMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainFragment : Fragment() {
    private var _binding : FragmentMainBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = context?.getSharedPreferences("deliveryProgramm", Context.MODE_PRIVATE)
        var isLogIn : Boolean = sharedPref?.getBoolean("isLogIn", false) ?: return
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putBoolean("isLogIn", true)
        editor.commit()

        val bottomNavigationView = binding.bottomNavigationM
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    replaceFragment(HomeFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_wallet -> {
                    replaceFragment(WalletFragment())
                    return@setOnNavigationItemSelectedListener true

                }
                R.id.navigation_track -> {
                    replaceFragment(TrackFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_profile -> {
                    replaceFragment(ProfileFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }
        bottomNavigationView.selectedItemId = R.id.navigation_home

        val profileSettingsSaved = getArguments()?.getString("pageMain")
        if (profileSettingsSaved == "profile") {
            bottomNavigationView.selectedItemId = R.id.navigation_profile
        }
        if (profileSettingsSaved == "track") {
            bottomNavigationView.selectedItemId = R.id.navigation_track
        }
        if (profileSettingsSaved == "home") {
            bottomNavigationView.selectedItemId = R.id.navigation_home
        }
        if (profileSettingsSaved == "wallet") {
            bottomNavigationView.selectedItemId = R.id.navigation_wallet
        }
    }
    private fun replaceFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_containerM, fragment)
            .commit()
    }
}