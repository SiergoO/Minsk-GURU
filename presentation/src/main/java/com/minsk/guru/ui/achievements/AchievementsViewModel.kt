package com.minsk.guru.ui.achievements

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.minsk.guru.domain.usecase.places.GetPlacesUseCase

class AchievementsViewModel(private val getPlacesUseCase: GetPlacesUseCase) : ViewModel() {

    var places: LiveData<String> = MutableLiveData()
    var placesNames: List<String> = listOf(
        "Кофейня",
        "Бар",
        "Столовая",
        "Пиццерия",
        "Суши-бар",
        "Шаурма",
        "Ресторан",
        "Кино",
        "Музей",
        "Театр",
        "Торговый центр",
        "Парк"
    )

    init {
        places = liveData {
            emit(getPlacesUseCase.getPlaces(placesNames).toString())
        }
//        viewModelScope.launch {
//            for (i in 0 until (placesNames.size)) {
//                Log.e("PLACES", getPlacesUseCase.getPlaces(placesNames).toString())
//            }
//        }
    }
}