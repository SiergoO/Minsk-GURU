package com.minsk.guru.ui.achievements

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.minsk.guru.domain.usecase.places.GetAchievementsUseCase

class AchievementsViewModel(private val getAchievementsUseCase: GetAchievementsUseCase) : ViewModel() {

    var places: LiveData<String> = MutableLiveData()

    init {
        places = liveData {
            emit(getAchievementsUseCase.getAchievements().toString())
        }
    }
}