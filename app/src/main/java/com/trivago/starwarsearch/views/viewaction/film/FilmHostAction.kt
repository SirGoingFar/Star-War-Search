package com.trivago.starwarsearch.views.viewaction.film

import com.trivago.starwarsearch.views.viewaction.BaseAction

sealed class FilmHostAction : BaseAction {
    class OpenFilmDetail(val url: String) : FilmHostAction()
}