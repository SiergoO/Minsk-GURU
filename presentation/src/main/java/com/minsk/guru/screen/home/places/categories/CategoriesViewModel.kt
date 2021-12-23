package com.minsk.guru.screen.home.places.categories

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingConfig
import com.firebase.ui.database.paging.DatabasePagingOptions
import com.google.android.gms.common.util.CollectionUtils
import com.google.firebase.database.*
import com.minsk.guru.BuildConfig
import com.minsk.guru.domain.adapter.UserIdHolder
import com.minsk.guru.domain.model.Category
import com.minsk.guru.domain.model.Place
import com.minsk.guru.domain.model.UserCategory
import com.minsk.guru.domain.usecase.places.GetVisitedPlacesUseCase
import com.minsk.guru.utils.TaskExecutorFactory
import com.minsk.guru.utils.createTaskExecutor
import com.minsk.guru.utils.showError
import com.minsk.guru.utils.singleResultUseCaseTaskProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

class CategoriesViewModel(
    private val userIdHolder: UserIdHolder,
    private val getVisitedPlacesUseCase: GetVisitedPlacesUseCase,
    private val taskExecutorFactory: TaskExecutorFactory
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

    private val taskGetVisitedPlacesByCategory = createGetVisitedPlacesTask()

    init {
        taskGetVisitedPlacesByCategory.start(GetVisitedPlacesUseCase.Param(userIdHolder.userId))
    }

    fun createPagingOptions(lifecycleOwner: LifecycleOwner, visitedPlaces: List<Place>) {
        FirebaseDatabase.getInstance(BuildConfig.FIREBASE_DATABASE_BASE_URL).reference.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val config = PagingConfig(10)
                    val query = snapshot.child("categories").ref
                    val options = DatabasePagingOptions.Builder<UserCategory>()
                        .setLifecycleOwner(lifecycleOwner)
                        .setQuery(query, config) { dataSnapshot ->
                            val categoryName = dataSnapshot.key ?: ""
                            dataSnapshot.getValue(object :
                                GenericTypeIndicator<List<Place>>() {})!!.let { places ->
                                UserCategory(categoryName, places, visitedPlaces.filter { it.category.contains(categoryName, true) })
                            }
                        }
                        .build()
                    _options.value = options
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