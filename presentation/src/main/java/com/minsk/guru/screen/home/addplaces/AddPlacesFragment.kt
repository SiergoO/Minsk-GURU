package com.minsk.guru.screen.home.addplaces

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.minsk.guru.BuildConfig
import com.minsk.guru.R
import com.minsk.guru.databinding.FragmentAddPlacesBinding
import com.minsk.guru.utils.map.getDefaultCameraPosition
import com.minsk.guru.utils.map.getPlaceMarkImage
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.MapType
import com.yandex.runtime.image.ImageProvider
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddPlacesFragment(private val layout: Int = R.layout.fragment_add_places) : Fragment(layout) {

    private val viewModel: AddPlacesViewModel by viewModel()

    private var _binding: FragmentAddPlacesBinding? = null
    val binding: FragmentAddPlacesBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        MapKitFactory.setApiKey(BuildConfig.YANDEX_MAPS_API_KEY)
        MapKitFactory.initialize(requireContext())
        super.onCreate(savedInstanceState)
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
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onStart() {
        binding.mapView.onStart()
        MapKitFactory.getInstance().onStart()
        super.onStart()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.mapView.map.apply {
            mapType = MapType.MAP
            move(
                getDefaultCameraPosition(),
                Animation(Animation.Type.SMOOTH, 2f),
                null
            )
        }
        viewModel.places.observe(viewLifecycleOwner) { places ->
            val placesPoints: List<Point> = places.map { Point(it.latitude, it.longitude) }
            val imageProvider = ImageProvider.fromBitmap(getPlaceMarkImage(false))
            binding.mapView.map.mapObjects.apply {
                addPlacemarks(placesPoints, imageProvider, IconStyle())
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStop() {
        binding.mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}