package com.backbase.citysearch.presentation.base

import android.os.Bundle
import android.view.View

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData

import com.backbase.citysearch.presentation.utils.Single

open class BaseFragment() : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    protected open fun initView() = Unit

    protected inline fun <T> LiveData<T>.subscribe(crossinline action: (T) -> Unit) {
        observe(viewLifecycleOwner) { it?.let(action) }
    }

    protected inline fun <T> LiveData<Single<T>>.subscribeToEvent(crossinline action: (T) -> Unit) {
        observe(viewLifecycleOwner) { it.getContentIfNotHandled()?.let(action) }
    }
}