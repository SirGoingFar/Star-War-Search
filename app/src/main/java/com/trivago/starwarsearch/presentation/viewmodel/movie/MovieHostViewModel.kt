package com.trivago.starwarsearch.presentation.viewmodel.movie

import com.trivago.starwarsearch.presentation.viewaction.movie.MovieHostAction
import com.trivago.starwarsearch.presentation.viewmodel.BaseViewModel
import com.trivago.starwarsearch.presentation.viewstate.movie.MovieHostState
import javax.inject.Inject

class MovieHostViewModel @Inject constructor() :
    BaseViewModel<MovieHostState, MovieHostAction>(MovieHostState()) {

    fun onFilmClicked(url: String) {
        performAction(MovieHostAction.OpenFilmDetail(url))
    }

}