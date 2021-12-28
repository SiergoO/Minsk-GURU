package com.minsk.guru.utils.map

import android.graphics.Bitmap
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import com.minsk.guru.R
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition

fun Fragment.getPlaceMarkImage(isPlaceVisited: Boolean): Bitmap? =
    ContextCompat.getDrawable(
        requireContext(),
        if (isPlaceVisited) R.drawable.ic_placemark_visited else R.drawable.ic_placemark_default
    )?.toBitmap(45, 64)

fun getDefaultCameraPosition(): CameraPosition =
    CameraPosition(Point(53.905275, 27.553534), 10.7f, 0f, 0f)