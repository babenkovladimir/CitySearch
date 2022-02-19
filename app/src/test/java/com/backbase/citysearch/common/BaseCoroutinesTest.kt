package com.backbase.citysearch.common

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before

@ExperimentalCoroutinesApi
open class BaseCoroutinesTest {

    protected val dispatcherProvider = TestDispatcherProvider()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcherProvider.testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}