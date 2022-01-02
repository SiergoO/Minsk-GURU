package com.minsk.guru.screen.home.addplaces.addplaces

import android.Manifest
import android.graphics.PointF
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.minsk.guru.R
import com.minsk.guru.databinding.FragmentAddPlacesBinding
import com.minsk.guru.model.toUiModel
import com.minsk.guru.utils.map.*
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.location.FilteringMode
import com.yandex.mapkit.location.LocationManager
import com.yandex.mapkit.location.LocationStatus
import com.yandex.mapkit.map.*
import com.yandex.runtime.image.ImageProvider
import com.yandex.runtime.ui_view.ViewProvider
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddPlacesFragment(private val layout: Int = R.layout.fragment_add_places) : Fragment(layout),
    ClusterListener, ClusterTapListener, MapObjectTapListener, CameraListener {

    companion object {
        private const val ANIMATION_DURATION_SEC = 1f
        private const val CLUSTER_RADIUS = 60.0
        private const val CLUSTER_MIN_ZOOM = 15
        private const val DESIRED_ACCURACY = 0.0
        private const val MINIMAL_TIME: Long = 500
        private const val MINIMAL_DISTANCE = 20.0
        private const val USE_IN_BACKGROUND = false
        private const val KEY_PLACE = "place"
        private const val KEY_IS_VISITED = "isVisited"
    }

    private val viewModel: AddPlacesViewModel by viewModel()

    private var _binding: FragmentAddPlacesBinding? = null
    val binding: FragmentAddPlacesBinding
        get() = _binding!!

    private var locationManager: LocationManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        MapKitFactory.initialize(activity?.applicationContext)
        locationManager = MapKitFactory.getInstance().createLocationManager()
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
        requestLocationPermissions()
        viewModel.renewVisitedPlaces()
        binding.mapView.map.apply {
            move(
                viewModel.lastCameraPosition ?: getDefaultCameraPosition(),
                Animation(
                    Animation.Type.SMOOTH,
                    viewModel.lastCameraPosition.let { if (it != null) 0f else ANIMATION_DURATION_SEC }),
                null
            )
            addCameraListener(this@AddPlacesFragment)
        }
        locationManager?.subscribeForLocationUpdates(
            DESIRED_ACCURACY,
            MINIMAL_TIME,
            MINIMAL_DISTANCE,
            USE_IN_BACKGROUND,
            FilteringMode.ON,
            userLocationListener
        )
        binding.btnMyLocation.setOnClickListener {
            if (viewModel.userPlaceMark != null) {
                binding.mapView.map.apply {
                    move(
                        getUserCameraPosition(
                            viewModel.userPlaceMark!!.geometry,
                            viewModel.lastCameraPosition?.let { if (it.zoom < 12f) 15f else it.zoom } ?: 15f
                        ),
                        Animation(Animation.Type.SMOOTH, ANIMATION_DURATION_SEC),
                        null
                    )
                }
            } else {
                locationManager?.requestSingleUpdate(
                    object : com.yandex.mapkit.location.LocationListener {

                        override fun onLocationUpdated(location: com.yandex.mapkit.location.Location) {
                            binding.mapView.map.apply {
                                move(
                                    getUserCameraPosition(location.position),
                                    Animation(Animation.Type.SMOOTH, ANIMATION_DURATION_SEC),
                                    null
                                )
                            }
                        }

                        override fun onLocationStatusUpdated(locationStatus: LocationStatus) {
                            // do nothing
                        }
                    })
            }
        }
        viewModel.visitedAndAllPlaces.observe(viewLifecycleOwner) { places ->
            if (places.first.isNotEmpty() && places.second.isNotEmpty()) {
                val visitedPlacesPoints: List<Point> = places.second.map { Point(it.latitude, it.longitude) }
                val placesPoints: List<Point> =
                    places.first.map { Point(it.latitude, it.longitude) }.filterNot { point ->
                        visitedPlacesPoints.any {
                            point.latitude == it.latitude && point.longitude == it.longitude
                        }
                    }
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
        binding.mapView.map.removeCameraListener(this)
        binding.mapView.map.mapObjects.removeTapListener(this)
        locationManager?.unsubscribe(userLocationListener)
        locationManager = null
        viewModel.lastCheckedPlaceMark = null
        viewModel.userPlaceMark = null
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
                        findViewById<TextView>(R.id.tv_place_name).text =
                            selectedPlace?.name ?: getString(R.string.unknown)
                        findViewById<TextView>(R.id.tv_place_address).text =
                            selectedPlace?.address ?: getString(R.string.unknown)
                        findViewById<TextView>(R.id.tv_place_category).text =
                            selectedPlace?.category ?: getString(R.string.unknown)
                    }))
                    zIndex = 2f
                    pmo.userData = "view"
                }
                else -> {
                    // do nothing
                }
            }
            if (pmo.userData == "icon") {
                val bundle = bundleOf(KEY_PLACE to selectedPlace?.toUiModel(), KEY_IS_VISITED to isVisited)
                findNavController().navigate(R.id.action_addPlacesFragment_to_placeDetailsFragment, bundle)
                pmo.userData = "icon"
            }
        }
        viewModel.lastCheckedPlaceMark = pmo
        return true
    }

    override fun onCameraPositionChanged(
        map: com.yandex.mapkit.map.Map,
        cameraPosition: CameraPosition,
        cameraUpdateReason: CameraUpdateReason,
        smth: Boolean
    ) {
        viewModel.lastCameraPosition = cameraPosition
    }

    private fun requestLocationPermissions() {
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                when {
                    permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                        binding.btnMyLocation.visibility = View.VISIBLE
                    }
                    permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                        binding.btnMyLocation.visibility = View.VISIBLE
                    }
                    else -> {
                        binding.btnMyLocation.visibility = View.GONE
                    }
                }
            }
        }
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
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

    private val userLocationListener = object : com.yandex.mapkit.location.LocationListener {

        override fun onLocationUpdated(location: com.yandex.mapkit.location.Location) {
            binding.mapView.map.mapObjects.apply {

                if (viewModel.userPlaceMark != null) {
                    remove(viewModel.userPlaceMark as MapObject)
                }
                addPlacemark(
                    location.position,
                    ImageProvider.fromBitmap(getUserLocationPlaceMarkImage()),
                    IconStyle().setAnchor(PointF(0.5f, 1f))
                ).also {
                    zIndex = 3f
                    viewModel.userPlaceMark = it
                }
            }
        }

        override fun onLocationStatusUpdated(locationStatus: LocationStatus) {
            // do nothing
        }
    }
}