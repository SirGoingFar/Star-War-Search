package com.trivago.starwarsearch.presentation.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trivago.starwarsearch.common.core.exception.Failure
import com.trivago.starwarsearch.common.core.exception.NetworkFailure
import com.trivago.starwarsearch.common.core.utils.ERROR_MESSAGE_BAD_INTERNET_CONNECTION
import com.trivago.starwarsearch.presentation.viewaction.BaseAction
import com.trivago.starwarsearch.presentation.viewstate.BaseViewState
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.properties.Delegates

abstract class BaseViewModel<
        ViewState : BaseViewState,
        ViewAction : BaseAction>(
    initialState: ViewState
) : ViewModel() {

    private val stateMutableLiveData = MutableLiveData<ViewState>()
    val stateLiveData: LiveData<ViewState> = stateMutableLiveData

    private val actionMutableLiveData = MutableLiveData<ViewAction>()
    val actionLiveData: LiveData<ViewAction> = actionMutableLiveData

    protected var state: ViewState by Delegates.observable(initialState) { _, _, new ->
        stateMutableLiveData.value = new
    }

    private var lastAction: ViewAction? = null

    @Inject
    lateinit var context: Context

    open fun onViewCreated(vararg input: Any? = emptyArray()) {
        onLoadData(input)
    }

    protected fun emit(viewAction: ViewAction) {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                state = onChangeState(viewAction)
            }
        }
    }

    protected fun performAction(viewAction: ViewAction, allowMultiple: Boolean = false) {
        lastAction?.let {
            if (!allowMultiple && lastAction == viewAction)
                return
        }

        lastAction = viewAction

        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                actionMutableLiveData.value = viewAction
            }
        }
    }

    protected fun doJobWithDispatcher(
        coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
        job: suspend () -> Unit,
        successJob: (suspend () -> Unit)? = null,
        failureJob: (suspend (throwable: Throwable) -> Unit)? = null
    ) = viewModelScope.launch(coroutineDispatcher) {
        runCatching {
            job.invoke()
        }.onSuccess {
            successJob?.invoke()
        }.onFailure {

            //This exception will be handled BY DEFAULT
            if (it is CancellationException)
                return@launch

            failureJob?.invoke(it) ?: it.printStackTrace()
        }
    }

    protected fun handleGeneralFailure(failure: Failure) {
        if (failure is NetworkFailure.Connection)
            toastMsg(ERROR_MESSAGE_BAD_INTERNET_CONNECTION, Toast.LENGTH_LONG)
    }

    protected fun toastMsg(text: Any, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, text.toString(), duration).show()
    }

    protected open fun onLoadData(vararg input: Any? = emptyArray()) {}

    protected open fun onChangeState(action: ViewAction): ViewState = state

}