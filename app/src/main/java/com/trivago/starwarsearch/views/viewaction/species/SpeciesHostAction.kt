package com.trivago.starwarsearch.views.viewaction.species

import com.trivago.starwarsearch.views.viewaction.BaseAction

sealed class SpeciesHostAction : BaseAction {
    class OpenSpecieDetail(val url: String) : SpeciesHostAction()
}