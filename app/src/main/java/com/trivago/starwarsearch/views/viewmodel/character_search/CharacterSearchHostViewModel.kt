package com.trivago.starwarsearch.views.viewmodel.character_search

import com.trivago.starwarsearch.views.viewaction.character_search.CharacterSearchHostAction
import com.trivago.starwarsearch.views.viewmodel.BaseViewModel
import com.trivago.starwarsearch.views.viewstate.character_search.CharacterSearchHostViewState
import javax.inject.Inject

class CharacterSearchHostViewModel @Inject constructor() :
    BaseViewModel<CharacterSearchHostViewState, CharacterSearchHostAction>(
        CharacterSearchHostViewState()
    ) {

    fun onCharacterClicked(url: String) {
        performAction(CharacterSearchHostAction.OpenCharacterDetail(url))
    }

}