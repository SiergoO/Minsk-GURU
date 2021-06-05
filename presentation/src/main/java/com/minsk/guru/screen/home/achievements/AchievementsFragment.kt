package com.minsk.guru.screen.home.achievements

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.minsk.guru.R
import com.minsk.guru.databinding.FragmentAchievementsBinding
import com.minsk.guru.domain.model.Achievement
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class AchievementsFragment(private val layout: Int = R.layout.fragment_achievements) :
    Fragment(layout) {

    private val viewModel: AchievementsViewModel by viewModel()
    private var _binding: FragmentAchievementsBinding? = null
    val binding: FragmentAchievementsBinding
        get() = _binding!!
    private lateinit var adapterAchievementsList: AdapterAchievementsList

    init {
        lifecycleScope.launchWhenResumed {
            withContext(coroutineContext) {
                val placesObserver = Observer<List<Achievement>> { places ->
                    adapterAchievementsList.set(places)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.achievements.apply {
            adapterAchievementsList = AdapterAchievementsList(context,
                object : AdapterAchievementsList.Callback {
                    override fun onPurchaseClicked(achievement: Achievement) {
                        // do smth on click
                    }
                })
            this.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            this.adapter = adapterAchievementsList
            setHasFixedSize(true)
            super.onViewCreated(view, savedInstanceState)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}