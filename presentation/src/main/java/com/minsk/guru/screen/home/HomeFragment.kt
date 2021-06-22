package com.minsk.guru.screen.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.minsk.guru.R
import com.minsk.guru.databinding.FragmentHomeBinding

class HomeFragment(private val layout: Int = R.layout.fragment_home) : Fragment(layout) {

    private var _viewbinding: FragmentHomeBinding? = null
    val viewbinding: FragmentHomeBinding
        get() = _viewbinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewbinding = DataBindingUtil.inflate(
            inflater,
            layout,
            container,
            false
        )
        viewbinding.lifecycleOwner = this
        return viewbinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewbinding.bottomNavigation.apply {
            setupWithNavController(findInnerNavController())
        }
    }

    private fun findInnerNavController(): NavController =
        (childFragmentManager.findFragmentById(R.id.home_nav_host_fragment) as NavHostFragment).navController
}