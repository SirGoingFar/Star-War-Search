package com.trivago.starwarsearch.presentation.viewaction.species

import com.trivago.starwarsearch.presentation.viewaction.BaseAction

sealed class SpeciesHostAction : BaseAction {
    class OpenSpecieDetail(val url: String) : SpeciesHostAction()
}