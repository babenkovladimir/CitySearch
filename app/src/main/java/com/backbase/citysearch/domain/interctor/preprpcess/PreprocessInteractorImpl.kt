package com.backbase.citysearch.domain.interctor.preprpcess

import com.backbase.citysearch.domain.repository.CityRepository

class PreprocessInteractorImpl(
    private val cityRepository: CityRepository,
) : PreprocessInteractor {

    override suspend fun prepareData() {
        return cityRepository.prepareData()
    }
}