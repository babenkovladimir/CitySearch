package com.backbase.citysearch.common

import com.backbase.citysearch.presentation.base.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher

@ExperimentalCoroutinesApi
class TestDispatcherProvider : DispatcherProvider {

    val testDispatcher = StandardTestDispatcher()

    override fun io(): CoroutineDispatcher = testDispatcher

    override fun main(): CoroutineDispatcher = testDispatcher

    override fun computation(): CoroutineDispatcher = testDispatcher
}