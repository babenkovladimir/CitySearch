package com.backbase.citysearch.domain.interctor.search

import com.backbase.citysearch.common.BaseCoroutinesTest
import com.backbase.citysearch.domain.entity.City
import com.backbase.citysearch.domain.repository.CityRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SearchInteractorImplTest : BaseCoroutinesTest() {

    @Mock
    lateinit var cityRepository: CityRepository

    private lateinit var sut: SearchInteractorImpl

    @Test
    fun `first init - city repository getDefaultList interaction`() = runTest {
        sut = SearchInteractorImpl(cityRepository, dispatcherProvider)
        sut.init()

        verify(cityRepository).provideDefaultList()
    }

    @Test
    fun `first init - return whole list`() = runTest {
        `when`(cityRepository.provideDefaultList()).thenReturn(testCityList)
        sut = SearchInteractorImpl(cityRepository, dispatcherProvider)
        sut.init()

        val data = sut.searchResultFlow().first()

        assertEquals(data.size, testCityList.size)
    }

    @Test
    fun `first init - return sorted list`() = runTest {
        `when`(cityRepository.provideDefaultList()).thenReturn(testCityList)
        sut = SearchInteractorImpl(cityRepository, dispatcherProvider)
        sut.init()

        val result = sut.searchResultFlow().first()

        val sortedResult = result.sortedBy { city -> city.searchKey }
        assertEquals(result, sortedResult)
    }

    @Test
    fun `filter with white space - return empty list`() = runTest {
        `when`(cityRepository.provideDefaultList()).thenReturn(testCityList)
        sut = SearchInteractorImpl(cityRepository, dispatcherProvider)
        sut.init()

        sut.filter(WHITE_SPACE)
        val data = sut.searchResultFlow().first()

        assertContentEquals(data, emptyList())
    }

    @Test
    fun `filter with character b - should return 7 sorted objects`() = runTest {
        `when`(cityRepository.provideDefaultList()).thenReturn(testCityList)
        sut = SearchInteractorImpl(cityRepository, dispatcherProvider)
        sut.init()

        sut.filter("b")
        val result = sut.searchResultFlow().first()
        val sortedResult = result.sortedBy { city -> city.searchKey }

        assertEquals(result.count(), 7)
        assertEquals(result, sortedResult)
    }

    @Test
    fun `filter with character n - should return 4 sorted objects`() = runTest {
        `when`(cityRepository.provideDefaultList()).thenReturn(testCityList)
        sut = SearchInteractorImpl(cityRepository, dispatcherProvider)
        sut.init()

        sut.filter("n")
        val result = sut.searchResultFlow().first()
        val sortedResult = result.sortedBy { city -> city.searchKey }

        assertEquals(result.count(), 4)
        assertEquals(result, sortedResult)
    }

    @Test
    fun `filter with character o - should not contain other first characters`() = runTest {
        `when`(cityRepository.provideDefaultList()).thenReturn(testCityList)
        sut = SearchInteractorImpl(cityRepository, dispatcherProvider)
        sut.init()

        sut.filter("o")
        val result = sut.searchResultFlow().first()
        val resultO = result.filterNot { it.name.startsWith("o") }

        assertEquals(result, resultO)
    }

    @Test
    fun `filter with character xyz - should return empty list`() = runTest {
        `when`(cityRepository.provideDefaultList()).thenReturn(testCityList)
        sut = SearchInteractorImpl(cityRepository, dispatcherProvider)
        sut.init()

        sut.filter("xyz")
        val result = sut.searchResultFlow().first()

        assertEquals(result, emptyList())
    }

    companion object {
        private const val WHITE_SPACE = " "
    }

}

/**
 * Test list already sorted
 */

val testCityList = listOf(
    City("LB", "Baabda", 9, LatLng(12.34, 10.34)),
    City("DE", "Baak", 13, LatLng(14.34, 10.34)),
    City("NL", "Baak", 8, LatLng(20.34, 10.34)),
    City("NL", "Baakwoning", 12, LatLng(-10.34, 23.34)),
    City("LB", "Baalbek", 7, LatLng(10.34, 10.34)),
    City("DE", "Baalberge", 14, LatLng(10.34, 10.34)),
    City("DE", "Baalborn", 6, LatLng(10.34, 10.34)),

    City("US", "New York Country", 15, LatLng(10.34, 10.34)),
    City("US", "New York Millis", 5, LatLng(10.34, 10.34)),
    City("US", "New York Millis", 16, LatLng(10.34, 10.34)),
    City("US", "New York", 4, LatLng(10.34, 10.34)),

    City("US", "Odessa (Historical)", 1, LatLng(10.34, 10.34)),
    City("UA", "Odessa", 17, LatLng(10.34, 10.34)),

    City("US", "Sydney", 3, LatLng(10.34, 10.34)),

    City("PL", "Warsaw", 18, LatLng(10.34, 10.34)),
    City("US", "Warsaw", 2, LatLng(10.34, 10.34))
)