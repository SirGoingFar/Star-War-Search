package com.trivago.starwarsearch.presentation.viewmodel.character_search

import com.trivago.starwarsearch.common.core.exception.EndOfListException
import com.trivago.starwarsearch.common.core.exception.Failure
import com.trivago.starwarsearch.common.core.exception.NetworkFailure
import com.trivago.starwarsearch.common.core.extension.cancelIfActive
import com.trivago.starwarsearch.common.core.interactor.UseCase
import com.trivago.starwarsearch.common.di.annotation.SearchScope
import com.trivago.starwarsearch.data.dto.character_search.Character
import com.trivago.starwarsearch.domain.usecase.character_search.FetchCachedCharacterList
import com.trivago.starwarsearch.domain.usecase.character_search.SearchCharacter
import com.trivago.starwarsearch.presentation.viewaction.character_search.CharacterSearchAction
import com.trivago.starwarsearch.presentation.viewmodel.BaseViewModel
import com.trivago.starwarsearch.presentation.viewstate.character_search.CharacterSearchState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import javax.inject.Inject

@SearchScope
class CharacterSearchViewModel @Inject constructor(
    private val searchCharacter: SearchCharacter,
    private val fetchCachedCharacterList: FetchCachedCharacterList
) : BaseViewModel<CharacterSearchState, CharacterSearchAction>(
    CharacterSearchState()
) {

    private var mIsDataLoaded: Boolean = false
    private var mTermSearchJob: Job? = null
    private var mSearchTerm: String? = null

    override fun onLoadData(vararg input: Any?) {
        doJobWithDispatcher(
            job = {
                fetchCachedCharacterList.execute(UseCase.None()).fold({}, {
                    if (it.isNotEmpty()) {
                        mIsDataLoaded = true
                        emit(CharacterSearchAction.RefreshCharacterList(it))
                    } else {
                        emit(CharacterSearchAction.SetQueryText(mSearchTerm ?: ""))
                    }
                })
            }, coroutineDispatcher = Dispatchers.Main
        )

    }

    override fun onChangeState(action: CharacterSearchAction): CharacterSearchState {
        return when (action) {

            is CharacterSearchAction.ToggleLoader -> state.copy(
                showLoader = action.show,
                searchTerm = mSearchTerm ?: ""
            )

            is CharacterSearchAction.RefreshCharacterList -> state.copy(
                showLoader = false,
                showEmptyState = false,
                characterList = action.newList,
                searchTerm = mSearchTerm ?: ""
            )

            is CharacterSearchAction.ShowEmptyState -> state.copy(
                showLoader = false,
                showEmptyState = true,
                title = action.title,
                body = action.body,
                characterList = emptyList(),
                searchTerm = mSearchTerm ?: ""
            )

            is CharacterSearchAction.SetQueryText -> state.copy(searchTerm = action.text)

        }
    }

    fun onSearchTextChanged(searchTerm: String?) {
        mTermSearchJob?.cancelIfActive()
        mSearchTerm = searchTerm

        if (searchTerm.isNullOrEmpty()) {
            emit(
                CharacterSearchAction.ShowEmptyState(
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
                emit(CharacterSearchAction.ToggleLoader(true))
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
            emit(CharacterSearchAction.RefreshCharacterList(list))
        }
    }

    private fun onSearchFailure(failure: Failure) {
        //Handle General error
        handleGeneralFailure(failure)

        if (failure is NetworkFailure.ApiCall) {
            if (failure.errorCode == 404) {
                emit(
                    CharacterSearchAction.ShowEmptyState(
                        "No character found", String.format(
                            "No character exists with name: '%s'",
                            mSearchTerm
                        )
                    )
                )
            }else{
                toastMsg(failure.errorMsg)
                emit(CharacterSearchAction.ToggleLoader(false))
            }
            return
        } else if (failure is EndOfListException) {
            emit(CharacterSearchAction.ToggleLoader(false))
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
        emit(CharacterSearchAction.ShowEmptyState(title, body))
    }

    companion object {
        private const val DATA_REQUEST_DELAY_TIME = 500L
    }
}
