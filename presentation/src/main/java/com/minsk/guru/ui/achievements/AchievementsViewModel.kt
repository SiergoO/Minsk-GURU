package com.minsk.guru.ui.achievements

import android.util.Log
import androidx.lifecycle.*
import com.minsk.guru.domain.usecase.places.GetPlacesUseCase
import kotlinx.coroutines.launch

class AchievementsViewModel(private val getPlacesUseCase: GetPlacesUseCase) : ViewModel() {

    var places: LiveData<String> = MutableLiveData<String>()

    init {
        places = liveData { emit(getPlacesUseCase.getPlaces("Музеи").toString()) }
        viewModelScope.launch {
            Log.e("PLACES", getPlacesUseCase.getPlaces("Музеи").toString())
        }
    }
}