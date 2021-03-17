package com.minsk.guru

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.minsk.guru.databinding.ActivityMainBinding
import com.minsk.guru.ui.base.BaseActivity


class MainActivity : BaseActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        NavigationUI.setupWithNavController(
            binding.bottomNavigation,
            getNavigationController()
        )
        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.achievements -> getNavigationController().navigate(R.id.achievementsFragment)
                R.id.places -> getNavigationController().navigate(R.id.placesFragment)
                R.id.profile -> getNavigationController().navigate(R.id.profileFragment)
            }
            true
        }
    }

    private fun getNavigationController(): NavController = binding.navHostFragment.findNavController()
}