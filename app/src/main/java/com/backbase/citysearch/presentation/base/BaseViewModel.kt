package com.backbase.citysearch.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.backbase.citysearch.presentation.utils.LiveEvent
import com.backbase.citysearch.presentation.utils.MutableLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.cancellation.CancellationException

open class BaseViewModel : ViewModel() {

    private val _error = MutableLiveEvent<Throwable>()
    val error: LiveEvent<Throwable> = _error

    protected fun <T> startCoroutine(
        context: CoroutineContext = EmptyCoroutineContext,
        block: suspend CoroutineScope.() -> T
    ) = viewModelScope.launch(context) {
        block()
    }

    protected fun <T> runCoroutine(
        context: CoroutineContext = EmptyCoroutineContext,
        block: suspend CoroutineScope.() -> T
    ) =
        viewModelScope.launch(context) {
            try {
                block()
            } catch (t: Throwable) {
                if (t !is CancellationException) {
                    _error.postTrigger(t)
                } else throw t
            }
        }
}