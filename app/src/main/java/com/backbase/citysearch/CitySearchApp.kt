package com.backbase.citysearch

import android.app.Application
import com.backbase.citysearch.common.commonModule
import com.backbase.citysearch.data.dataModule
import com.backbase.citysearch.domain.domainModule
import com.backbase.citysearch.presentation.presentationModule
import kotlinx.serialization.ExperimentalSerializationApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@ExperimentalSerializationApi
class CitySearchApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@CitySearchApp)
            modules(koinModules)
        }
    }

    private val koinModules = listOf(
        commonModule,
        presentationModule,
        domainModule,
        dataModule
    )
}