package com.minsk.guru.screen

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.minsk.guru.R
import com.minsk.guru.databinding.ActivityMainBinding
import com.minsk.guru.screen.base.BaseActivity

class MainActivity: BaseActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
    }
}
