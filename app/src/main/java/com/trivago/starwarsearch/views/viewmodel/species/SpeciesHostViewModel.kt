package com.trivago.starwarsearch.views.viewmodel.species

import com.trivago.starwarsearch.views.viewaction.species.SpeciesHostAction
import com.trivago.starwarsearch.views.viewmodel.BaseViewModel
import com.trivago.starwarsearch.views.viewstate.species.SpeciesHostState
import javax.inject.Inject

class SpeciesHostViewModel @Inject constructor() :
    BaseViewModel<SpeciesHostState, SpeciesHostAction>(SpeciesHostState()) {

    fun onSpecieClicked(url: String) {
        performAction(SpeciesHostAction.OpenSpecieDetail(url))
    }

}