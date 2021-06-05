package com.minsk.guru.screen.home.places.places

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.minsk.guru.domain.usecase.places.GetPlacesUseCase

class PlacesViewModel(private val getPlacesUseCase: GetPlacesUseCase) : ViewModel() {

    var places: LiveData<String> = MutableLiveData()

    init {
        places = liveData {
            emit(getPlacesUseCase.getPlaces().toString())
        }
    }
}