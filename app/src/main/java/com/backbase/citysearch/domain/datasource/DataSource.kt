package com.backbase.citysearch.domain.datasource

import com.backbase.citysearch.data.pojo.CityPojo

interface DataSource {

    fun provideData(fileName: String): List<CityPojo>
}