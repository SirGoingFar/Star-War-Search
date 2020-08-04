package com.trivago.starwarsearch.views.viewaction.species

import com.trivago.starwarsearch.domain.dto.common.ListItem
import com.trivago.starwarsearch.views.viewaction.BaseAction

sealed class SpeciesListAction : BaseAction {
    class UpdateSpecieList(val list: List<ListItem>) : SpeciesListAction()
}