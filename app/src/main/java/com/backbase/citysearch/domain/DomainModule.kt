package com.backbase.citysearch.domain

import com.backbase.citysearch.domain.interctor.preprpcess.PreprocessInteractor
import com.backbase.citysearch.domain.interctor.preprpcess.PreprocessInteractorImpl
import com.backbase.citysearch.domain.interctor.search.SearchInteractor
import com.backbase.citysearch.domain.interctor.search.SearchInteractorImpl
import org.koin.dsl.bind
import org.koin.dsl.module

val domainModule = module {

    factory { PreprocessInteractorImpl(get()) } bind PreprocessInteractor::class
    factory { SearchInteractorImpl(get(), get()) } bind SearchInteractor::class
}