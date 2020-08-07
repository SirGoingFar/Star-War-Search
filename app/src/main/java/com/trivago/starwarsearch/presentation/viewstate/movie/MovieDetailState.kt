package com.trivago.starwarsearch.presentation.viewstate.movie

import com.trivago.starwarsearch.data.dto.film.Movie
import com.trivago.starwarsearch.presentation.viewstate.BaseViewState

data class MovieDetailState(
    val showLoader: Boolean = true,
    val film: Movie? = null
) : BaseViewState