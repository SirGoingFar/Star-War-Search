package com.trivago.starwarsearch.presentation.viewaction.character_search

import com.trivago.starwarsearch.presentation.viewaction.BaseAction

sealed class CharacterSearchHostAction :
    BaseAction {
    class OpenCharacterDetail(val characterUrl: String) : CharacterSearchHostAction()
}