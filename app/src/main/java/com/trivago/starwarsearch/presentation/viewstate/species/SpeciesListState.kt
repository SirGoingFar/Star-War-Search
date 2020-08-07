package com.trivago.starwarsearch.presentation.viewstate.species

import com.trivago.starwarsearch.data.dto.common.ListItem
import com.trivago.starwarsearch.presentation.viewstate.BaseViewState

data class SpeciesListState(
    val list: List<ListItem> = emptyList()
) : BaseViewState