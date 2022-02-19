package com.backbase.citysearch.domain.repository

import com.backbase.citysearch.domain.entity.City

interface CityRepository {

    suspend fun prepareData()

    fun provideDefaultList(): List<City>
}