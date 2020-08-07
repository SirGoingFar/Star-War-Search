package com.trivago.starwarsearch.presentation.viewmodel.character_search

import com.trivago.starwarsearch.presentation.viewaction.character_search.CharacterSearchHostAction
import com.trivago.starwarsearch.presentation.viewmodel.BaseViewModel
import com.trivago.starwarsearch.presentation.viewstate.character_search.CharacterSearchHostViewState
import javax.inject.Inject

class CharacterSearchHostViewModel @Inject constructor() :
    BaseViewModel<CharacterSearchHostViewState, CharacterSearchHostAction>(
        CharacterSearchHostViewState()
    ) {

    fun onCharacterClicked(url: String) {
        performAction(CharacterSearchHostAction.OpenCharacterDetail(url))
    }

}