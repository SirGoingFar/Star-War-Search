package com.trivago.starwarsearch.views.viewaction.species

import com.trivago.starwarsearch.domain.dto.specie.Specie
import com.trivago.starwarsearch.views.viewaction.BaseAction

sealed class SpecieDetailAction : BaseAction {
    class UpdateSpecielDetail(val specie: Specie) : SpecieDetailAction()
    class UpdatePlanetPopulation(val population: String) : SpecieDetailAction()
    class SpecieDetailFetchUnsuccessful(val msg: String) : SpecieDetailAction()
}