package com.trivago.starwarsearch.presentation.viewstate.species

import com.trivago.starwarsearch.data.dto.specie.Specie
import com.trivago.starwarsearch.presentation.viewstate.BaseViewState

data class SpecieDetailState(
    val errorMsg:String? = null,
    val showLoader: Boolean = true,
    val specie: Specie? = null
) : BaseViewState