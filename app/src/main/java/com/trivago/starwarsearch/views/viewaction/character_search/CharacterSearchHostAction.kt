package com.trivago.starwarsearch.views.viewaction.character_search

import com.trivago.starwarsearch.views.viewaction.BaseAction

sealed class CharacterSearchHostAction :
    BaseAction {
    class OpenCharacterDetail(val characterUrl: String) : CharacterSearchHostAction()
}