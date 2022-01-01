package com.minsk.guru.screen.home.addplaces

import android.graphics.PointF
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.minsk.guru.BuildConfig
import com.minsk.guru.R
import com.minsk.guru.databinding.FragmentAddPlacesBinding
import com.minsk.guru.utils.map.*
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.*
import com.yandex.runtime.image.ImageProvider
import com.yandex.runtime.ui_view.ViewProvider
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddPlacesFragment(private val layout: Int = R.layout.fragment_add_places) : Fragment(layout),
    ClusterListener, ClusterTapListener, MapObjectTapListener {

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

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.mapView.map.apply {
            mapType = MapType.MAP
            move(
                getDefaultCameraPosition(),
                Animation(Animation.Type.SMOOTH, ANIMATION_DURATION_SEC),
                null
            )
        }
        viewModel.visitedAndAllPlaces.observe(viewLifecycleOwner) { places ->
            if (places.first.isNotEmpty() && places.second.isNotEmpty()) {
                val visitedPlacesPoints: List<Point> = places.second.map { Point(it.latitude, it.longitude) }
                val placesPoints: List<Point> =
                    places.first.map { Point(it.latitude, it.longitude) }.filterNot { point -> visitedPlacesPoints.any { point.latitude == it.latitude && point.longitude == it.longitude} }
                val imageProviderDefault = ImageProvider.fromBitmap(getPlaceMarkImage(false))
                val imageProviderVisited = ImageProvider.fromBitmap(getPlaceMarkImage(true))
                binding.mapView.map.mapObjects.addClusterizedPlacemarkCollection(this).apply {
                    addPlacemarks(placesPoints, imageProviderDefault, getDefaultIconStyle()).forEach {
                        it.userData = "icon"
                        it.zIndex = 1f
                    }
                    addPlacemarks(visitedPlacesPoints, imageProviderVisited, getDefaultIconStyle()).forEach {
                        it.userData = "icon"
                        it.zIndex = 1f
                    }
                    clusterPlacemarks(CLUSTER_RADIUS, CLUSTER_MIN_ZOOM)
                    addTapListener(this@AddPlacesFragment)
                }
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

    override fun onClusterAdded(cluster: Cluster) {
        cluster.apply {
            appearance.setIcon(TextImageProvider(requireActivity(), cluster.size.toString()))
            addClusterTapListener(this@AddPlacesFragment)
        }
    }

    override fun onClusterTap(cluster: Cluster): Boolean {
        Toast.makeText(
            context,
            "Tapped cluster with ${cluster.size} elements inside",
            Toast.LENGTH_SHORT
        ).show()
        return true
    }

    companion object {
        private const val ANIMATION_DURATION_SEC = 2f
        private const val CLUSTER_RADIUS = 60.0
        private const val CLUSTER_MIN_ZOOM = 15
    }

    override fun onMapObjectTap(mapObject: MapObject, point: Point): Boolean {
        val pmo = mapObject as PlacemarkMapObject
        clearLastSelectedPositionPlaceMarkView(pmo)
        val selectedPlace = pmo.getPlace(viewModel.places.value)
        val isVisited = viewModel.visitedPlaces.value?.any { it.id == selectedPlace?.id } ?: false
        pmo.apply {
            when (userData) {
                "view" -> {
                    setIcon(
                        ImageProvider.fromBitmap(getPlaceMarkImage(isVisited)),
                        IconStyle().setAnchor(PointF(0.5f, 1f))
                    )
                    zIndex = 1f
                    pmo.userData = "icon"
                }
                "icon" -> {
                    val placeInfoView = layoutInflater.inflate(R.layout.layout_place_info, binding.mapView, false)
                    setView(ViewProvider(placeInfoView.apply {
                        findViewById<TextView>(R.id.tv_place_name).text = selectedPlace?.name ?: getString(R.string.unknown)
                        findViewById<TextView>(R.id.tv_place_address).text = selectedPlace?.address ?: getString(R.string.unknown)
                        findViewById<TextView>(R.id.tv_place_category).text = selectedPlace?.category ?: getString(R.string.unknown)
                    }))
                    zIndex = 2f
                    pmo.userData = "view"
                }
                else -> {
                    // do nothing
                }
            }
            if (pmo.userData == "icon") {
                // Nav to prove visited screen
                pmo.userData = "icon"
            }
        }
        viewModel.lastCheckedPlaceMark = pmo
        return true
    }

    private fun clearLastSelectedPositionPlaceMarkView(pmo: PlacemarkMapObject) {
        viewModel.lastCheckedPlaceMark?.let {
            val lastSelectedPlace = it.getPlace(viewModel.places.value)
            val isVisited =
                viewModel.visitedPlaces.value?.any { visited -> visited.id == lastSelectedPlace?.id } ?: false
            it.userData = pmo.userData
            it.zIndex = 1f
            it.setIcon(
                ImageProvider.fromBitmap(getPlaceMarkImage(isVisited)),
                IconStyle().setAnchor(PointF(0.5f, 1f))
            )
        }
    }
}