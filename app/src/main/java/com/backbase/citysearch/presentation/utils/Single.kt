package com.backbase.citysearch.presentation.utils

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData

open class Single<out T>(private val content: T) {
    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    fun peekContent(): T = content
}

open class LiveEvent<T> : LiveData<Single<T>>()

class MutableLiveEvent<T> : LiveEvent<T>() {
    @MainThread
    fun trigger(datum: T) {
        value = Single(datum)
    }

    fun postTrigger(datum: T) {
        postValue(Single(datum))
    }
}