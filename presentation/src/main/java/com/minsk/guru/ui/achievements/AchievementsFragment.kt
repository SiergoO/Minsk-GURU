package com.minsk.guru.ui.achievements

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.minsk.guru.R
import com.minsk.guru.databinding.FragmentAchievementsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AchievementsFragment(private val layout: Int = R.layout.fragment_achievements) : Fragment(layout) {

    private val viewModel: AchievementsViewModel by viewModel()

    private var _binding: FragmentAchievementsBinding? = null
    val binding: FragmentAchievementsBinding
        get() = _binding!!

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