package com.trivago.starwarsearch.presentation.activity

import androidx.lifecycle.Observer
import com.trivago.starwarsearch.presentation.viewaction.BaseAction
import com.trivago.starwarsearch.presentation.viewstate.BaseViewState

abstract class BaseInjectableActivity<ViewState : BaseViewState, ViewAction : BaseAction> :
    BaseActivity() {

    protected val stateObserver = Observer<ViewState> {
        onStateChange(it)
    }

    protected val actionObserver = Observer<ViewAction> {
        onPerformAction(it)
    }

    open fun onStateChange(viewState: ViewState){}

    open fun onPerformAction(viewAction: ViewAction){}

}