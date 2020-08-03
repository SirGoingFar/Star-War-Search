package com.trivago.starwarsearch.views.viewaction

sealed class CharacterSearchHostAction : BaseAction {
    class OpenCharacterDetail(val characterUrl: String) : CharacterSearchHostAction()
}