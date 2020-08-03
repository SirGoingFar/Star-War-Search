package com.trivago.starwarsearch.views.viewmodel

import com.trivago.starwarsearch.views.viewaction.CharacterSearchHostAction
import com.trivago.starwarsearch.views.viewstate.CharacterSearchHostViewState
import javax.inject.Inject

class CharacterSearchHostViewModel @Inject constructor() :
    BaseViewModel<CharacterSearchHostViewState, CharacterSearchHostAction>(
        CharacterSearchHostViewState()
    ) {

    fun onCharacterClicked(url: String) {
        performAction(CharacterSearchHostAction.OpenCharacterDetail(url))
    }

}