package com.trivago.starwarsearch.views.viewmodel.movie

import com.trivago.starwarsearch.views.viewaction.movie.MovieHostAction
import com.trivago.starwarsearch.views.viewmodel.BaseViewModel
import com.trivago.starwarsearch.views.viewstate.movie.MovieHostState
import javax.inject.Inject

class MovieHostViewModel @Inject constructor() :
    BaseViewModel<MovieHostState, MovieHostAction>(MovieHostState()) {

    fun onFilmClicked(url: String) {
        performAction(MovieHostAction.OpenFilmDetail(url))
    }

}