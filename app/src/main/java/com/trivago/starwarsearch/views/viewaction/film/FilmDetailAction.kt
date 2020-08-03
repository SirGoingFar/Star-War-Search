package com.trivago.starwarsearch.views.viewaction.film

import com.trivago.starwarsearch.domain.dto.film.Film
import com.trivago.starwarsearch.views.viewaction.BaseAction

sealed class FilmDetailAction : BaseAction {
    class UpdateFilmDetail(val film: Film) : FilmDetailAction()
    class FilmDetailLoadError(val msg: String) : FilmDetailAction()
}