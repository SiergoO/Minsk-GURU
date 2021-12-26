package com.minsk.guru.screen.home.places.categories

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingConfig
import com.firebase.ui.database.paging.DatabasePagingOptions
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.database.*
import com.minsk.guru.BuildConfig
import com.minsk.guru.domain.adapter.UserIdHolder
import com.minsk.guru.domain.model.Place
import com.minsk.guru.domain.model.UserCategory
import com.minsk.guru.domain.usecase.places.GetVisitedPlacesUseCase
import com.minsk.guru.utils.TaskExecutorFactory
import com.minsk.guru.utils.createTaskExecutor
import com.minsk.guru.utils.singleResultUseCaseTaskProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

class CategoriesViewModel(
    private val userIdHolder: UserIdHolder,
    private val getVisitedPlacesUseCase: GetVisitedPlacesUseCase,
    private val taskExecutorFactory: TaskExecutorFactory,
    private val firebaseAnalytics: FirebaseAnalytics,
    private val firebaseCrashlytics: FirebaseCrashlytics
) : ViewModel() {

    private val _visitedPlaces = MutableLiveData<List<Place>>()
    val visitedPlaces: LiveData<List<Place>>
        get() = _visitedPlaces

    private val _options = MutableLiveData<DatabasePagingOptions<UserCategory>>()
    val options: LiveData<DatabasePagingOptions<UserCategory>>
        get() = _options

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable>
        get() = _error

    private val taskGetVisitedPlaces = createGetVisitedPlacesTask()

    fun fetchVisitedPlaces() {
        taskGetVisitedPlaces.start(GetVisitedPlacesUseCase.Param(userIdHolder.userId))
    }

    // Unfortunately, putting this logic to repository won't make more readable, so implemented logic here
    // Throwing lifecycleOwner in parameters to viewModel is wrong according to MVVM architecture. Getting rid of it cause crashes.
    fun createPagingOptions(lifecycleOwner: LifecycleOwner) {
        FirebaseDatabase.getInstance(BuildConfig.FIREBASE_DATABASE_BASE_URL).reference.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    fetchVisitedPlaces()
                    val config = PagingConfig(20, 20, true, 30)
                    val query = snapshot.child("categories").ref
                        val options = DatabasePagingOptions.Builder<UserCategory>()
                            .setLifecycleOwner(lifecycleOwner)
                            .setQuery(query, config) { dataSnapshot ->
                                val categoryName = dataSnapshot.key ?: ""
                                dataSnapshot.getValue(object :
                                    GenericTypeIndicator<List<Place>>() {})!!.let { places ->
                                    UserCategory(
                                        categoryName,
                                        places,
                                        visitedPlaces.value?.filter {
                                            it.category.contains(
                                                categoryName,
                                                true
                                            )
                                        }?: listOf())
                                }
                            }
                            .build()
                    if (_options.value == null) {
                        _options.value = options
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    _error.value = error.toException()
                }
            })
    }

    suspend fun handleLoadingStates(loadStateFlow: Flow<CombinedLoadStates>?) {
        loadStateFlow?.collectLatest { loadStates ->
            when (loadStates.refresh) {
                is LoadState.Error -> {
                    _error.value = (loadStates.append as LoadState.Error).error
                }
                is LoadState.Loading -> {
                    _isLoading.value = true
                }
                is LoadState.NotLoading -> {
                    _isLoading.value = false
                }
            }

            when (loadStates.append) {
                is LoadState.Error -> {
                    _error.value = (loadStates.append as LoadState.Error).error
                }
                is LoadState.Loading -> {
                    _isLoading.value = true
                }
                is LoadState.NotLoading -> {
                    if (loadStates.append.endOfPaginationReached) {
                        _isLoading.value = false
                    }
                    if (loadStates.refresh is LoadState.NotLoading) {
                        _isLoading.value = false
                    }
                }
            }
        }
    }

    fun logEvent(name: String, params: Bundle?) = firebaseAnalytics.logEvent(name, params)

    fun logError(message: String) = firebaseCrashlytics.log(message)

    private fun handleError(error: Throwable) {
        _error.value = error
    }

    private fun handleGetVisitedPlacesResult(data: GetVisitedPlacesUseCase.Result?) {
        when (data) {
            is GetVisitedPlacesUseCase.Result.Failure -> _error.value = data.error
            is GetVisitedPlacesUseCase.Result.Success -> _visitedPlaces.value = data.visitedPlaces
            else -> Unit
        }
    }

    private fun createGetVisitedPlacesTask() =
        taskExecutorFactory.createTaskExecutor<GetVisitedPlacesUseCase.Param, GetVisitedPlacesUseCase.Result>(
            singleResultUseCaseTaskProvider { getVisitedPlacesUseCase },
            { data -> handleGetVisitedPlacesResult(data) },
            { error -> handleError(error) }
        )
}