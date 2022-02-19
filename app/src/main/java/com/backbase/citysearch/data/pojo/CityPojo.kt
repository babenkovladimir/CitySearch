package com.backbase.citysearch.data.pojo


@kotlinx.serialization.Serializable
data class CityPojo(
    val country: String,
    val name: String,
    val _id: Int,
    val coord: Coordinate
)

@kotlinx.serialization.Serializable
data class Coordinate(
    val lon: Double,
    val lat: Double
)