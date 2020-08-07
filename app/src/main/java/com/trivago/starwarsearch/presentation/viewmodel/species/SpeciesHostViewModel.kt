package com.trivago.starwarsearch.presentation.viewmodel.species

import com.trivago.starwarsearch.presentation.viewaction.species.SpeciesHostAction
import com.trivago.starwarsearch.presentation.viewmodel.BaseViewModel
import com.trivago.starwarsearch.presentation.viewstate.species.SpeciesHostState
import javax.inject.Inject

class SpeciesHostViewModel @Inject constructor() :
    BaseViewModel<SpeciesHostState, SpeciesHostAction>(SpeciesHostState()) {

    fun onSpecieClicked(url: String) {
        performAction(SpeciesHostAction.OpenSpecieDetail(url))
    }

}