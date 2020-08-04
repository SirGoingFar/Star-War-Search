package com.trivago.starwarsearch.views.viewmodel.movie

import com.trivago.starwarsearch.core.extension.getValueAt
import com.trivago.starwarsearch.domain.usecase.movie.FetchMovieItemsByCharacterUrl
import com.trivago.starwarsearch.views.viewaction.movie.MovieListViewAction
import com.trivago.starwarsearch.views.viewmodel.BaseViewModel
import com.trivago.starwarsearch.views.viewstate.movie.MovieListState
import javax.inject.Inject

class MovieListViewModel @Inject constructor(
    private val fetchFilmItemsByCharacterUrl: FetchMovieItemsByCharacterUrl
) : BaseViewModel<MovieListState, MovieListViewAction>(MovieListState()) {

    override fun onViewCreated(vararg input: Any?) {
        fetchFilmItems((input.getValueAt(0)) as String)
    }

    override fun onChangeState(action: MovieListViewAction): MovieListState {
        return when (action) {
            is MovieListViewAction.UpdateFilmList -> state.copy(list = action.list)
        }
    }

    private fun fetchFilmItems(url: String) {
        doJobWithDispatcher(job = {
            fetchFilmItemsByCharacterUrl.execute(url).fold({},
                {
                    emit(MovieListViewAction.UpdateFilmList(it))
                }
            )
        })
    }

}