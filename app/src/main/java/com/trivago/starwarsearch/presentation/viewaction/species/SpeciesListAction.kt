package com.trivago.starwarsearch.presentation.viewaction.species

import com.trivago.starwarsearch.data.dto.common.ListItem
import com.trivago.starwarsearch.presentation.viewaction.BaseAction

sealed class SpeciesListAction : BaseAction {
    class UpdateSpecieList(val list: List<ListItem>) : SpeciesListAction()
}