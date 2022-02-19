package com.backbase.citysearch.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.backbase.citysearch.domain.entity.City
import com.backbase.citysearch.domain.interctor.search.SearchInteractor
import com.backbase.citysearch.presentation.base.BaseViewModel
import com.backbase.citysearch.presentation.navigation.MapNavigationCommand
import com.backbase.citysearch.presentation.navigation.NavCommand
import com.backbase.citysearch.presentation.utils.LiveEvent
import com.backbase.citysearch.presentation.utils.MutableLiveEvent
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect

class SearchViewModel(
    private val searchInteractor: SearchInteractor,
) : BaseViewModel() {

    private var searchJob: Job? = null

    private val _navigationEvent = MutableLiveEvent<NavCommand>()
    val navEvent: LiveEvent<NavCommand> = _navigationEvent

    private val _citySearchResult = MutableLiveData<List<City>>()
    val citySearchResult: LiveData<List<City>> = _citySearchResult

    init {
        runCoroutine {
            searchInteractor.init()
        }
        observeResults()
    }

    private fun observeResults() {
        runCoroutine {
            searchInteractor.searchResultFlow().collect { citiesList ->
                _citySearchResult.postValue(citiesList)
            }
        }
    }

    fun proceedSearch(request: String) {
        searchJob?.cancel()
        searchJob = runCoroutine {
            searchInteractor.filter(request)
        }
    }

    fun onCityClick(city: City) {
        _navigationEvent.trigger(MapNavigationCommand(city))
    }

    override fun onCleared() {
        super.onCleared()
        searchJob?.cancel()
    }
}