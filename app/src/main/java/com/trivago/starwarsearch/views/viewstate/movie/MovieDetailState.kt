package com.trivago.starwarsearch.views.viewstate.movie

import com.trivago.starwarsearch.domain.dto.film.Movie
import com.trivago.starwarsearch.views.viewstate.BaseViewState

data class MovieDetailState(
    val showLoader: Boolean = true,
    val film: Movie? = null
) : BaseViewState