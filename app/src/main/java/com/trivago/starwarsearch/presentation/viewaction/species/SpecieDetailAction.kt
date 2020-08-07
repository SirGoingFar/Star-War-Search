package com.trivago.starwarsearch.presentation.viewaction.species

import com.trivago.starwarsearch.data.dto.specie.Specie
import com.trivago.starwarsearch.presentation.viewaction.BaseAction

sealed class SpecieDetailAction : BaseAction {
    class UpdateSpecielDetail(val specie: Specie) : SpecieDetailAction()
    class UpdatePlanetDetail(val population: String, val name:String) : SpecieDetailAction()
    class SpecieDetailFetchUnsuccessful(val msg: String) : SpecieDetailAction()
}