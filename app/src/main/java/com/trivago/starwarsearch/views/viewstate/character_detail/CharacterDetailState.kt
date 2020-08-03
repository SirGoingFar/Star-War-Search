package com.trivago.starwarsearch.views.viewstate.character_detail

import com.trivago.starwarsearch.views.viewstate.BaseViewState

data class CharacterDetailState(
    val url:String? = null,
    val name: String? = null,
    val birthYear: String? = null,
    val height: String? = null,
    val gender: String? = null,
    val hasSpecies: Boolean = false,
    val hasFilm: Boolean = false
) : BaseViewState