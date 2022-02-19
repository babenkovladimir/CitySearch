package com.backbase.citysearch.data

import com.backbase.citysearch.data.local.AssetsDataSourceImpl
import com.backbase.citysearch.data.repository.CityRepositoryImpl
import com.backbase.citysearch.domain.datasource.DataSource
import com.backbase.citysearch.domain.repository.CityRepository
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.koin.dsl.bind
import org.koin.dsl.module


@ExperimentalSerializationApi
val dataModule = module {
    single { Json { ignoreUnknownKeys = true } } bind Json::class


    single { CityRepositoryImpl(get()) } bind CityRepository::class
    single { AssetsDataSourceImpl(get(), get()) } bind DataSource::class
}