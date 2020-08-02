package com.trivago.starwarsearch.views.viewmodel

import com.trivago.starwarsearch.core.extension.getValueAt
import com.trivago.starwarsearch.domain.usecase.FetchCharacterByUrl
import com.trivago.starwarsearch.views.viewaction.CharacterDetailAction
import com.trivago.starwarsearch.views.viewstate.CharacterDetailState
import javax.inject.Inject

class CharacterDetailViewModel @Inject constructor(
    private val fetchCardById: FetchCharacterByUrl
) : BaseViewModel<CharacterDetailState, CharacterDetailAction>(CharacterDetailState()) {

    override fun onLoadData(vararg input: Any?) {
        fetchCardForId(input.getValueAt(0) as String)
    }

    override fun onChangeState(action: CharacterDetailAction): CharacterDetailState {
        return when (action) {
            is CharacterDetailAction.SetCharacterDetail -> state.copy(character = action.character)
        }
    }

    private fun fetchCardForId(cardId: String) {
        doJobWithDispatcher(
            job = {
                fetchCardById.execute(cardId).fold({}, {
                    emit(CharacterDetailAction.SetCharacterDetail(it))
                })
            }
        )
    }
}