package com.minsk.guru.screen.home.places.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.minsk.guru.domain.model.Achievement
import com.minsk.guru.domain.usecase.firebase.achievements.GetAchievementsUseCase

class CategoriesViewModel(private val getAchievementsUseCase: GetAchievementsUseCase) : ViewModel() {

    var places: LiveData<List<Achievement>> = MutableLiveData()

    init {
        places = liveData {
            emit(getAchievementsUseCase.getAchievements().achievements)
        }
    }
}