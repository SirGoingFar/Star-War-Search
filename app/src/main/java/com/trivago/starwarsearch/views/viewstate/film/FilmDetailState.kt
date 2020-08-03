package com.trivago.starwarsearch.views.viewstate.film

import com.trivago.starwarsearch.domain.dto.film.Film
import com.trivago.starwarsearch.views.viewstate.BaseViewState

data class FilmDetailState(
    val showLoader: Boolean = true,
    val film: Film? = null
) : BaseViewState