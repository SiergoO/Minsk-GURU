package com.minsk.guru.ui.home

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.minsk.guru.R
import com.minsk.guru.databinding.ActivityHomeBinding
import com.minsk.guru.ui.home.base.BaseActivity

class HomeActivity : BaseActivity() {

    lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_home
        )
    }

    override fun onStart() {
        super.onStart()
        NavigationUI.setupWithNavController(
            binding.bottomNavigation,
            getNavigationController()
        )
        getNavigationController().setGraph(R.navigation.navigation_home)
    }

    private fun getNavigationController(): NavController =
        binding.homeNavHostFragment.findNavController()
}