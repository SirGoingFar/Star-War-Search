package com.trivago.starwarsearch.views.viewstate.species

import com.trivago.starwarsearch.domain.dto.specie.Specie
import com.trivago.starwarsearch.views.viewstate.BaseViewState

data class SpecieDetailState(
    val showLoader: Boolean = true,
    val specie: Specie? = null
) : BaseViewState