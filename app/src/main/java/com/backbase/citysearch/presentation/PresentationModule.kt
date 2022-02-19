package com.backbase.citysearch.presentation

import com.backbase.citysearch.domain.entity.City
import com.backbase.citysearch.presentation.map.MapViewModel
import com.backbase.citysearch.presentation.search.SearchViewModel
import com.backbase.citysearch.presentation.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel { SplashViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { (city: City) -> MapViewModel(city) }
}