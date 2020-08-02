package com.trivago.starwarsearch.views.viewstate

import com.trivago.starwarsearch.domain.dto.Character

data class CharacterListState(
    val searchTerm: String = "",
    val showLoader: Boolean = false,
    val showEmptyState: Boolean = true,
    val title: String = "No character listed",
    val body: String = "Enter character name above.",
    val characterList: List<Character> = emptyList()
) : BaseViewState