package com.backbase.citysearch.presentation.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.backbase.citysearch.domain.entity.City
import com.backbase.citysearch.presentation.base.BaseViewModel

class MapViewModel(
    city: City,
) : BaseViewModel(
) {

    private val _cityToShow = MutableLiveData<City>()
    val cityToShow: LiveData<City> = _cityToShow

    init {
        _cityToShow.postValue(city)
    }
}