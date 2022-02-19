package com.backbase.citysearch.domain.interctor.search

import com.backbase.citysearch.domain.entity.City
import kotlinx.coroutines.flow.Flow


interface SearchInteractor {

    suspend fun init()

    suspend fun searchResultFlow(): Flow<List<City>>

    suspend fun filter(query: String)
}