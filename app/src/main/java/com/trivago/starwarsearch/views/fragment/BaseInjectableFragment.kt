package com.trivago.starwarsearch.views.fragment

import androidx.lifecycle.Observer
import com.trivago.starwarsearch.views.viewaction.BaseAction
import com.trivago.starwarsearch.views.viewstate.BaseViewState

abstract class BaseInjectableFragment<ViewState : BaseViewState, ViewAction : BaseAction> :
    BaseFragment() {

    protected val stateObserver = Observer<ViewState> {
        onStateChange(it)
    }

    protected val actionObserver = Observer<ViewAction> {
        onPerformAction(it)
    }

    open fun onStateChange(viewState: ViewState) {}

    open fun onPerformAction(viewAction: ViewAction) {}

}