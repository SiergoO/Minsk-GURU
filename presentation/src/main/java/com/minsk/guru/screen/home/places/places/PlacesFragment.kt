package com.minsk.guru.screen.home.places.places

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.minsk.guru.R
import com.minsk.guru.databinding.FragmentPlacesBinding
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlacesFragment(private val layout: Int = R.layout.fragment_places) : Fragment(layout) {

    private val viewModel: PlacesViewModel by viewModel()

    private var _binding: FragmentPlacesBinding? = null
    val binding: FragmentPlacesBinding
        get() = _binding!!

    init {
        lifecycleScope.launchWhenStarted {
            withContext(coroutineContext) {
                val placesObserver = Observer<String> { places ->
                    binding.tvPlaces.text = places
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
}