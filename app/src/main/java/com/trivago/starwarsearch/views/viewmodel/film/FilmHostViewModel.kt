package com.trivago.starwarsearch.views.viewmodel.film

import com.trivago.starwarsearch.views.viewaction.film.FilmHostAction
import com.trivago.starwarsearch.views.viewmodel.BaseViewModel
import com.trivago.starwarsearch.views.viewstate.film.FilmHostState
import javax.inject.Inject

class FilmHostViewModel @Inject constructor() :
    BaseViewModel<FilmHostState, FilmHostAction>(FilmHostState()) {

    fun onFilmClicked(url: String) {
        performAction(FilmHostAction.OpenFilmDetail(url))
    }

}