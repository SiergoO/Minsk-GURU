package com.minsk.guru.ui.achievements

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.minsk.guru.domain.usecase.places.GetPlacesUseCase

class AchievementsViewModel(private val getPlacesUseCase: GetPlacesUseCase) : ViewModel() {

    var places: LiveData<String> = MutableLiveData<String>()

    init {
        places = liveData { emit(getPlacesUseCase.getPlaces("Музеи").toString()) }
//      Log.e("PLACES", getPlacesUseCase.getPlaces("Музеи").toString())
    }
}