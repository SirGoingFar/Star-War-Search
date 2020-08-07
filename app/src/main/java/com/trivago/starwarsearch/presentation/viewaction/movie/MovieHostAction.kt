package com.trivago.starwarsearch.presentation.viewaction.movie

import com.trivago.starwarsearch.presentation.viewaction.BaseAction

sealed class MovieHostAction : BaseAction {
    class OpenFilmDetail(val url: String) : MovieHostAction()
}