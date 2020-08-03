package com.trivago.starwarsearch.views.viewstate

data class CharacterDetailState(
    val name: String? = null,
    val birthYear: String? = null,
    val height: String? = null,
    val gender: String? = null,
    val hasSpecies: Boolean = false,
    val hasFilm: Boolean = false
) : BaseViewState