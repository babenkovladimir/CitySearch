package com.backbase.citysearch.data.local

import android.content.Context
import com.backbase.citysearch.data.pojo.CityPojo
import com.backbase.citysearch.domain.datasource.DataSource
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.IOException

@ExperimentalSerializationApi
class AssetsDataSourceImpl(
    private val context: Context,
    private val json: Json,
) : DataSource {

    override fun provideData(fileName: String): List<CityPojo> {
        return try {
            val data = context.assets.open(fileName).use { inputStream ->
                json.decodeFromStream<List<CityPojo>>(inputStream)
            }
            data
        } catch (e: IOException) {
            // Log error
            emptyList()
        }
    }
}