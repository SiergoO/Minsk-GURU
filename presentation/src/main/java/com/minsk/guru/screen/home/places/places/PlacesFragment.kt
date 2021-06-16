package com.minsk.guru.screen.home.places.places

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.minsk.guru.R
import com.minsk.guru.databinding.FragmentPlacesBinding
import com.minsk.guru.domain.model.Place
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlacesFragment(private val layout: Int = R.layout.fragment_places) : Fragment(layout) {

    private val viewModel: PlacesViewModel by viewModel()

    private var _binding: FragmentPlacesBinding? = null
    val binding: FragmentPlacesBinding
        get() = _binding!!
    private lateinit var placesAdapter: PlacesAdapter

    init {
        lifecycleScope.launchWhenStarted {
            withContext(coroutineContext) {
                viewModel.getPlacesByCategory(arguments?.getString("categoryName"))
                val placesObserver = Observer<List<Place>> { places ->
                    placesAdapter.set(places)
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
        binding.places.apply {
            placesAdapter = PlacesAdapter(context, object : PlacesAdapter.OnPlaceClickListener {
                override fun onPlaceClicked(place: Place) {
                    Toast.makeText(context, "${place.id} clicked", Toast.LENGTH_SHORT).show()
                }
            }, object : PlacesAdapter.OnIsVisitedCheckboxClickListener {
                override fun onIsVisitedCheckboxClicked(place: Place, isVisited: Boolean) {
                    viewModel.updateLocalPlace(place, isVisited)
                }
            })
            this.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            this.adapter = placesAdapter
            setHasFixedSize(true)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}