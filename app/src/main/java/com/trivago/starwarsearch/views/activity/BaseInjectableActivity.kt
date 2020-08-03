package com.trivago.starwarsearch.views.activity

import androidx.lifecycle.Observer
import com.trivago.starwarsearch.views.viewaction.BaseAction
import com.trivago.starwarsearch.views.viewstate.BaseViewState

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