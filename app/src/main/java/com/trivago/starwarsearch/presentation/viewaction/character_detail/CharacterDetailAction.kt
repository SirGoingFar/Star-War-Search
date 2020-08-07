package com.trivago.starwarsearch.presentation.viewaction.character_detail

import com.trivago.starwarsearch.presentation.viewaction.BaseAction

sealed class CharacterDetailAction :
    BaseAction {
    class SetCharacterDetail(
        val url: String? = null,
        val name: String? = null,
        val birthYear: String? = null,
        val height: String? = null,
        val gender: String? = null,
        val seeMoreText: String? = null,
        val hasSpecies: Boolean = false,
        val hasFilm: Boolean = false
    ) : CharacterDetailAction()
}