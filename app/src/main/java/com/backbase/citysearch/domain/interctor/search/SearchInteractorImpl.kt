package com.backbase.citysearch.domain.interctor.search

import com.backbase.citysearch.data.repository.CityRepositoryImpl
import com.backbase.citysearch.domain.entity.City
import com.backbase.citysearch.domain.repository.CityRepository
import com.backbase.citysearch.presentation.base.DispatcherProvider
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext


class SearchInteractorImpl(
    private val cityRepository: CityRepository,
    private val dispatchersProvider: DispatcherProvider,
) : SearchInteractor {

    private val _resultsFlow = MutableStateFlow(emptyList<City>())
    override suspend fun searchResultFlow(): Flow<List<City>> = _resultsFlow

    override suspend fun init() {
        _resultsFlow.value = cityRepository.provideDefaultList()
    }

    /**
     * Binary algorithm is used for sorting.
     * The list of cities is already sorted in the repository.
     *
     * The sorted list must match the following conditions - the range of cities whose name starts with the query parameter is displayed.
     * If the request is empty, then the full list is displayed
     *
     * The [City.country] parameter does not take part in the filtering, but took part in the sorting of data in the [CityRepositoryImpl]
     *
     * If the request is empty, then the full list is displayed
     */

    override suspend fun filter(query: String) {
        withContext(dispatchersProvider.computation()) {

            val defaultList = cityRepository.provideDefaultList()


            val startIndexBinary = defaultList.binarySearch { city, index ->
                val startsWith = city.name.startsWith(query, ignoreCase = true)
                val previousMatch = if (index == 0) {
                    false
                } else {
                    defaultList[index - 1].name.startsWith(query, ignoreCase = true)
                }

                if (startsWith) {
                    if (previousMatch.not()) {
                        0
                    } else {
                        1
                    }
                } else {
                    city.name.compareTo(query, ignoreCase = true)
                }
            }

            ensureActive()

            val endIndexBinary = defaultList.binarySearch { city, index ->
                val startsWith = city.name.startsWith(query, ignoreCase = true)
                val nextMatch = if (index == defaultList.lastIndex) {
                    false
                } else {
                    defaultList[index + 1].name.startsWith(query, ignoreCase = true)
                }

                if (startsWith) {
                    if (nextMatch.not()) {
                        0
                    } else {
                        -1
                    }
                } else {
                    city.name.compareTo(query, ignoreCase = true)
                }
            }

            ensureActive()

            val newResults = when {
                startIndexBinary < 0 || endIndexBinary < 0 -> emptyList()
                startIndexBinary == endIndexBinary -> listOf(defaultList[startIndexBinary])
                startIndexBinary >= 0 && endIndexBinary <= defaultList.size -> defaultList.subList(startIndexBinary, endIndexBinary + 1)
                else -> {
                    emptyList()
                }
            }

            _resultsFlow.value = newResults
        }
    }

    private fun <T> List<T>.binarySearch(comparison: (T, Int) -> Int): Int {
        var low = 0
        var high = lastIndex

        while (low <= high) {
            val mid = (low + high).ushr(1) // safe from overflows
            val midVal = get(mid)
            val cmp = comparison(midVal, mid)

            when {
                cmp < 0 -> low = mid + 1
                cmp > 0 -> high = mid - 1
                else -> return mid
            } // key found
        }
        return -(low + 1)  // key not found
    }
}