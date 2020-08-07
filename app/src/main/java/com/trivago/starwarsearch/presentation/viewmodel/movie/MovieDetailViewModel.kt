package com.trivago.starwarsearch.presentation.viewmodel.movie

import com.trivago.starwarsearch.common.core.extension.cancelIfActive
import com.trivago.starwarsearch.common.core.extension.getValueAt
import com.trivago.starwarsearch.domain.usecase.movie.FetchMovieDetailByUrl
import com.trivago.starwarsearch.presentation.viewaction.movie.MovieDetailAction
import com.trivago.starwarsearch.presentation.viewmodel.BaseViewModel
import com.trivago.starwarsearch.presentation.viewstate.movie.MovieDetailState
import kotlinx.coroutines.Job
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(
    private val fetchFilmDetailByUrl: FetchMovieDetailByUrl
) : BaseViewModel<MovieDetailState, MovieDetailAction>(MovieDetailState()) {

    private var fetchFilmDetailJob: Job? = null

    override fun onViewCreated(vararg input: Any?) {
        fetchFilmDetailByUrl(input.getValueAt(0) as String)
    }

    override fun onChangeState(action: MovieDetailAction): MovieDetailState {
        return when (action) {
            is MovieDetailAction.UpdateFilmDetail -> state.copy(
                showLoader = false,
                film = action.film
            )
            else -> state
        }
    }

    private fun fetchFilmDetailByUrl(url: String) {
        fetchFilmDetailJob?.cancelIfActive()
        fetchFilmDetailJob = doJobWithDispatcher(
            job = {
                fetchFilmDetailByUrl.execute(url).fold(
                    {
                        performAction(MovieDetailAction.FilmDetailLoadError("Unable to load film details"))
                    },
                    {
                        emit(MovieDetailAction.UpdateFilmDetail(film = it))
                    }
                )
            }
        )
    }
}