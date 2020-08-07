package com.trivago.starwarsearch.presentation.viewstate.character_search

import com.trivago.starwarsearch.data.dto.character_search.Character
import com.trivago.starwarsearch.presentation.viewstate.BaseViewState

data class CharacterSearchState(
    val searchTerm: String = "",
    val showLoader: Boolean = false,
    val showEmptyState: Boolean = true,
    val title: String = "No character listed",
    val body: String = "Enter character name above.",
    val characterList: List<Character> = emptyList()
) : BaseViewState