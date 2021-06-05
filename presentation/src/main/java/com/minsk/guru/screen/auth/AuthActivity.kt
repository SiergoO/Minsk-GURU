package com.minsk.guru.screen.auth

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.minsk.guru.R
import com.minsk.guru.databinding.ActivityAuthBinding
import com.minsk.guru.screen.home.base.BaseActivity

class AuthActivity: BaseActivity() {
    lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_auth
        )
    }
}
