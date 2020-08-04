package com.trivago.starwarsearch.views.viewaction.movie

import com.trivago.starwarsearch.domain.dto.film.Movie
import com.trivago.starwarsearch.views.viewaction.BaseAction

sealed class MovieDetailAction : BaseAction {
    class UpdateFilmDetail(val film: Movie) : MovieDetailAction()
    class FilmDetailLoadError(val msg: String) : MovieDetailAction()
}