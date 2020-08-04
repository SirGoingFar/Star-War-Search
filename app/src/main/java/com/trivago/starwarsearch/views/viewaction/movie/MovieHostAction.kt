package com.trivago.starwarsearch.views.viewaction.movie

import com.trivago.starwarsearch.views.viewaction.BaseAction

sealed class MovieHostAction : BaseAction {
    class OpenFilmDetail(val url: String) : MovieHostAction()
}