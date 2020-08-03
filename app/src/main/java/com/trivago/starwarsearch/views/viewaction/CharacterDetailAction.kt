package com.trivago.starwarsearch.views.viewaction

sealed class CharacterDetailAction : BaseAction {
    class SetCharacterDetail(
        val name: String? = null,
        val birthYear: String? = null,
        val height: String? = null,
        val gender: String? = null,
        val hasSpecies: Boolean = false,
        val hasFilm: Boolean = false
    ) : CharacterDetailAction()
}