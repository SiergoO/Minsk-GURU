package com.minsk.guru.screen.home.places.places

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.minsk.guru.R
import com.minsk.guru.databinding.FragmentPlacesBinding
import com.minsk.guru.domain.model.Place
import com.minsk.guru.model.toDomainModel
import com.minsk.guru.utils.showError
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlacesFragment(private val layout: Int = R.layout.fragment_places) : Fragment(layout) {

    private val viewModel: PlacesViewModel by viewModel()

    private var _binding: FragmentPlacesBinding? = null
    val binding: FragmentPlacesBinding
        get() = _binding!!

    private var placesAdapter: PlacesAdapter? = null

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
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val category =
            requireArguments().getParcelable<com.minsk.guru.model.UserCategory>(KEY_USER_CATEGORY)!!
                .toDomainModel()
        viewModel.visitedPlaces.observe(viewLifecycleOwner) { visitedPlaces ->
            placesAdapter!!.setVisitedPlaces(visitedPlaces)
        }
        viewModel.error.observe(viewLifecycleOwner) { error ->
            viewModel.logError(error.message ?: getString(R.string.error_default))
            showError(error)
        }
        binding.places.apply {
            placesAdapter = PlacesAdapter(
                context,
                object : PlacesAdapter.OnPlaceClickListener {
                    override fun onPlaceClicked(place: Place) {
                        Toast.makeText(context, "${place.id} clicked", Toast.LENGTH_SHORT).show()
                    }
                },
                object : PlacesAdapter.OnIsVisitedCheckboxClickListener {
                    override fun onIsVisitedCheckboxClicked(place: Place, isVisited: Boolean) {
                        viewModel.logEvent(
                            EVENT_PLACE_VISITED_CHECKBOX_CHECKED,
                            bundleOf(Pair(ARG_PLACE_NAME, place.name))
                        )
                        viewModel.updateLocalPlace(place, category.name, isVisited)
                    }
                })
            this.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            this.adapter = placesAdapter
            setHasFixedSize(true)
        }
        placesAdapter!!.set(category.categoryPlaces, category.visitedPlaces)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        placesAdapter = null
    }

    companion object {
        private const val KEY_USER_CATEGORY = "userCategory"
        private const val EVENT_PLACE_VISITED_CHECKBOX_CHECKED = "placeVisitedCheckboxChecked"
        private const val ARG_PLACE_NAME = "placeName"
    }
}