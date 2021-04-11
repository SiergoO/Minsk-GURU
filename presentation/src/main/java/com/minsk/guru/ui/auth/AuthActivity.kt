package com.minsk.guru.ui.auth

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.minsk.guru.R
import com.minsk.guru.databinding.ActivityAuthBinding
import com.minsk.guru.ui.home.base.BaseActivity

class AuthActivity: BaseActivity() {
    lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_auth
        )
    }

    private fun getNavigationController(): NavController =
        binding.authNavHostFragment.findNavController()
}
