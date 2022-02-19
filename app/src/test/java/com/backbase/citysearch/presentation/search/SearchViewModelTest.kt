package com.backbase.citysearch.presentation.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.backbase.citysearch.common.BaseCoroutinesTest
import com.backbase.citysearch.domain.entity.City
import com.backbase.citysearch.domain.interctor.search.SearchInteractor
import com.backbase.citysearch.getOrAwaitValue
import com.backbase.citysearch.presentation.navigation.MapNavigationCommand
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest : BaseCoroutinesTest() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var searchInteractor: SearchInteractor

    private lateinit var sut: SearchViewModel

    @Test
    fun `city click - navigation command with city`() {
        sut = SearchViewModel(searchInteractor)

        val testCity = fakeList.first()
        sut.onCityClick(testCity)

        val navigationValue = sut.navEvent.getOrAwaitValue()

        assert(navigationValue.peekContent() is MapNavigationCommand)
        val navParam = (navigationValue.peekContent() as MapNavigationCommand).city

        assertEquals(testCity, navParam)
    }
}

val fakeList = listOf(
    City("US", "Odessa (Historical)", 1, LatLng(10.34, 10.34)),
)