package com.trivago.starwarsearch.views.viewmodel

import com.trivago.starwarsearch.R
import com.trivago.starwarsearch.core.exception.EndOfListException
import com.trivago.starwarsearch.core.exception.Failure
import com.trivago.starwarsearch.core.exception.NetworkFailure
import com.trivago.starwarsearch.core.extension.cancelIfActive
import com.trivago.starwarsearch.core.interactor.UseCase
import com.trivago.starwarsearch.di.annotation.CharacterSearchScope
import com.trivago.starwarsearch.domain.dto.Character
import com.trivago.starwarsearch.domain.usecase.FetchCachedCharacterList
import com.trivago.starwarsearch.domain.usecase.SearchCharacter
import com.trivago.starwarsearch.views.viewaction.CharacterListAction
import com.trivago.starwarsearch.views.viewstate.CharacterListState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import javax.inject.Inject

@CharacterSearchScope
class CharacterListViewModel @Inject constructor(
    private val searchCharacter: SearchCharacter,
    private val fetchCachedCharacterList: FetchCachedCharacterList
) : BaseViewModel<CharacterListState, CharacterListAction>(CharacterListState()) {

    private var mIsDataLoaded: Boolean = false
    private var mTermSearchJob: Job? = null
    private var mSearchTerm: String? = null

    override fun onLoadData(vararg input: Any?) {
        doJobWithDispatcher(
            job = {
                fetchCachedCharacterList.execute(UseCase.None()).fold({}, {
                    if (it.isNotEmpty()) {
                        mIsDataLoaded = true
                        emit(CharacterListAction.RefreshCharacterList(it))
                    } else {
                        emit(CharacterListAction.SetQueryText(mSearchTerm ?: ""))
                    }
                })
            }
        )

    }

    override fun onChangeState(action: CharacterListAction): CharacterListState {
        return when (action) {

            is CharacterListAction.ToggleLoader -> state.copy(
                showLoader = action.show,
                searchTerm = mSearchTerm ?: ""
            )

            is CharacterListAction.RefreshCharacterList -> state.copy(
                showLoader = false,
                showEmptyState = false,
                characterList = action.newList,
                searchTerm = mSearchTerm ?: ""
            )

            is CharacterListAction.ShowEmptyState -> state.copy(
                showLoader = false,
                showEmptyState = true,
                title = action.title,
                body = action.body,
                characterList = emptyList(),
                searchTerm = mSearchTerm ?: ""
            )

            is CharacterListAction.SetQueryText -> state.copy(searchTerm = action.text)

        }
    }

    fun onSearchTextChanged(searchTerm: String?) {
        mTermSearchJob?.cancelIfActive()
        mSearchTerm = searchTerm

        if (searchTerm.isNullOrEmpty()) {
            emit(
                CharacterListAction.ShowEmptyState(
                    "No character listed",
                    "Enter character name above."
                )
            )
            return
        }

        mTermSearchJob = doJobWithDispatcher(
            job = {
                //Delay the data request should the search term changes
                delay(DATA_REQUEST_DELAY_TIME)

                //Ensure Coroutine Scope conform to cancellation order - if given
                yield()

                //Make API call to do a search
                emit(CharacterListAction.ToggleLoader(true))
                searchCharacter.execute(searchTerm).fold(::onSearchFailure, ::onSearchSuccessful)
            }
        )
    }

    fun onListScrollToBase() {
        onSearchTextChanged(mSearchTerm)
    }

    private fun onSearchSuccessful(list: List<Character>) {
        if (list.isEmpty())
            resolveScreenState(true)
        else {
            mIsDataLoaded = true
            emit(CharacterListAction.RefreshCharacterList(list))
        }
    }

    private fun onSearchFailure(failure: Failure) {
        //Handle General error
        handleGeneralFailure(failure)

        if (failure is NetworkFailure.ApiCall) {
            if (failure.errorCode == 404) {
                emit(
                    CharacterListAction.ShowEmptyState(
                        "No character found", String.format(
                            "No character exists with name: '%s'",
                            mSearchTerm
                        )
                    )
                )
            }
            return
        } else if (failure is EndOfListException) {
            emit(CharacterListAction.ToggleLoader(false))
            return
        }

        resolveScreenState()
    }

    private fun resolveScreenState(successful: Boolean = false) {
        /*if (mIsDataLoaded) {
            if (!successful)
                toastMsg(context.getString(R.string.text_something_wrong))
            emit(CharacterListAction.ToggleLoader(false))
        } else {
            val title = if (successful) "No character found" else "No character listed"
            val body = if (successful) String.format(
                "No character exists with name: '%s'",
                mSearchTerm
            ) else "Enter character name above."
            emit(CharacterListAction.ShowEmptyState(title, body))
        }*/
        val title = if (successful) "No character found" else "No character listed"
        val body = if (successful) String.format(
            "No character exists with name: '%s'",
            mSearchTerm
        ) else "Enter character name above."
        emit(CharacterListAction.ShowEmptyState(title, body))
    }

    companion object {
        private const val DATA_REQUEST_DELAY_TIME = 500L
    }
}
