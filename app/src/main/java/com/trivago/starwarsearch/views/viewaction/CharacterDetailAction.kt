package com.trivago.starwarsearch.views.viewaction

import com.trivago.starwarsearch.domain.dto.Character

sealed class CharacterDetailAction : BaseAction {
    class SetCharacterDetail(val character: Character) : CharacterDetailAction()
}