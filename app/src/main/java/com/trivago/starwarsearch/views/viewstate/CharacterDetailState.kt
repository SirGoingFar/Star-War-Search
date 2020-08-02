package com.trivago.starwarsearch.views.viewstate

import com.trivago.starwarsearch.domain.dto.Character

data class CharacterDetailState(
    val character: Character? = null
) : BaseViewState