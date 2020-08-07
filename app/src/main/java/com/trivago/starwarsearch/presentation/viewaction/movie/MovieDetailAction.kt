package com.trivago.starwarsearch.presentation.viewaction.movie

import com.trivago.starwarsearch.data.dto.film.Movie
import com.trivago.starwarsearch.presentation.viewaction.BaseAction

sealed class MovieDetailAction : BaseAction {
    class UpdateFilmDetail(val film: Movie) : MovieDetailAction()
    class FilmDetailLoadError(val msg: String) : MovieDetailAction()
}