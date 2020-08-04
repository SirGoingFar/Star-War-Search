package com.trivago.starwarsearch.views.viewstate.species

import com.trivago.starwarsearch.domain.dto.common.ListItem
import com.trivago.starwarsearch.views.viewstate.BaseViewState

data class SpeciesListState(
    val list: List<ListItem> = emptyList()
) : BaseViewState