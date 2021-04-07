package com.minsk.guru.ui.achievements

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.minsk.guru.R
import com.minsk.guru.databinding.FragmentAchievementsBinding
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class AchievementsFragment(private val layout: Int = R.layout.fragment_achievements) :
    Fragment(layout) {

    private val viewModel: AchievementsViewModel by viewModel()

    private var _binding: FragmentAchievementsBinding? = null
    val binding: FragmentAchievementsBinding
        get() = _binding!!

    init {
        lifecycleScope.launchWhenStarted {
            withContext(coroutineContext) {
                val placesObserver = Observer<String> { places ->
                    binding.tvAchievements.text = places
                }
                viewModel.places.observe(viewLifecycleOwner, placesObserver)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(
            inflater,
            layout,
            container,
            false
        )
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}