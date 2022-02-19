package com.backbase.citysearch.data.repository

import com.backbase.citysearch.common.BaseCoroutinesTest
import com.backbase.citysearch.data.Constants.LOCAL_DATA_FILE_NAME
import com.backbase.citysearch.domain.datasource.DataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.ExperimentalSerializationApi
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
@OptIn(ExperimentalSerializationApi::class)
@RunWith(MockitoJUnitRunner::class)
class CityRepositoryImplTest : BaseCoroutinesTest() {

    @Mock
    lateinit var dataSource: DataSource

    private lateinit var sut: CityRepositoryImpl

    @Test
    fun `get unsorted list - return sorted list`() = runTest {
        `when`(dataSource.provideData(LOCAL_DATA_FILE_NAME)).thenReturn(unsortedCities)

        sut = CityRepositoryImpl(dataSource)
        sut.prepareData()
        val result = sut.provideDefaultList()

        val sortedResult = result.sortedBy { city -> city.searchKey }

        assertEquals(result, sortedResult)
    }
}