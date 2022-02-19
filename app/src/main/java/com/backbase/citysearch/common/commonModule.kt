package com.backbase.citysearch.common

import com.backbase.citysearch.presentation.base.DispatcherProvider
import org.koin.dsl.bind
import org.koin.dsl.module

val commonModule = module {
    single { DispatcherProvider.CoroutineDispatcherProvider() } bind DispatcherProvider::class
}