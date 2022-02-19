package com.backbase.citysearch.data.repository

import com.backbase.citysearch.data.Constants.LOCAL_DATA_FILE_NAME
import com.backbase.citysearch.data.mapper.toCity
import com.backbase.citysearch.domain.datasource.DataSource
import com.backbase.citysearch.domain.entity.City
import com.backbase.citysearch.domain.interctor.search.SearchInteractorImpl
import com.backbase.citysearch.domain.repository.CityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class CityRepositoryImpl(
    private val dataSource: DataSource,
) : CityRepository {

    private var cities: List<City> = emptyList()

    /**
     * The binary search algorithm is used in [SearchInteractorImpl]. Accordingly, the initial data must be sorted.
     * It will also make it possible to display the sorted list on the screen without additional operations.
     * @return sorted List
     */

    override suspend fun prepareData() {
        val items = withContext(Dispatchers.Default) {
            dataSource.provideData(LOCAL_DATA_FILE_NAME).map { it.toCity() }.sortedBy { it.searchKey }
        }
        cities = items
    }

    override fun provideDefaultList(): List<City> {
        return cities
    }
}