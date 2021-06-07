package com.minsk.guru.screen.home.places.places

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.minsk.guru.domain.model.Place
import com.minsk.guru.domain.model.Places
import com.minsk.guru.domain.usecase.firebase.places.GetPlacesUseCase

class PlacesViewModel(private val getPlacesUseCase: GetPlacesUseCase) : ViewModel() {

    var places: LiveData<List<Place>> = MutableLiveData()

    fun getPlacesByCategory(categoryName: String?) {
        places = liveData {
            emit(getPlacesUseCase.getPlacesByCategory(categoryName))
        }
    }
}