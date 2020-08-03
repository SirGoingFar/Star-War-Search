package com.trivago.starwarsearch.views.viewstate.character_search

import com.trivago.starwarsearch.domain.dto.Character
import com.trivago.starwarsearch.views.viewstate.BaseViewState

data class CharacterSearchState(
    val searchTerm: String = "",
    val showLoader: Boolean = false,
    val showEmptyState: Boolean = true,
    val title: String = "No character listed",
    val body: String = "Enter character name above.",
    val characterList: List<Character> = emptyList()
) : BaseViewState