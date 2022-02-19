package com.backbase.citysearch.presentation.splash

import com.backbase.citysearch.domain.interctor.preprpcess.PreprocessInteractor
import com.backbase.citysearch.presentation.base.BaseViewModel
import com.backbase.citysearch.presentation.navigation.NavCommand
import com.backbase.citysearch.presentation.navigation.SearchNavigationCommand
import com.backbase.citysearch.presentation.utils.LiveEvent
import com.backbase.citysearch.presentation.utils.MutableLiveEvent

class SplashViewModel(
    private val preprocessInteractor: PreprocessInteractor,
) : BaseViewModel() {

    private val _navigation = MutableLiveEvent<NavCommand>()
    val navigation: LiveEvent<NavCommand> = _navigation

    init {
        runCoroutine {
            preprocessInteractor.prepareData()
            _navigation.postTrigger(SearchNavigationCommand)
        }
    }
}