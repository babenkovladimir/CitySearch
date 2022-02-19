package com.backbase.citysearch.data.mapper

import com.backbase.citysearch.data.pojo.CityPojo
import com.backbase.citysearch.domain.entity.City
import com.google.android.gms.maps.model.LatLng

fun CityPojo.toCity(): City {
    return City(
        country = this.country,
        name = this.name,
        id = this._id,
        coordinates = LatLng(this.coord.lat, this.coord.lon)
    )
}