package com.backbase.citysearch.domain.entity

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize


@Parcelize
data class City(
    val country: String,
    val name: String,
    val id: Int,
    val coordinates: LatLng,
) : Parcelable {

    @IgnoredOnParcel
    val searchKey: String = name.plus(country).lowercase()
}