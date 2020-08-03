package com.trivago.starwarsearch.views.viewmodel.film

import com.trivago.starwarsearch.core.extension.cancelIfActive
import com.trivago.starwarsearch.core.extension.getValueAt
import com.trivago.starwarsearch.domain.usecase.film.FetchFilmDetailByUrl
import com.trivago.starwarsearch.views.viewaction.film.FilmDetailAction
import com.trivago.starwarsearch.views.viewmodel.BaseViewModel
import com.trivago.starwarsearch.views.viewstate.film.FilmDetailState
import kotlinx.coroutines.Job
import javax.inject.Inject

class FilmDetailViewModel @Inject constructor(
    private val fetchFilmDetailByUrl: FetchFilmDetailByUrl
) : BaseViewModel<FilmDetailState, FilmDetailAction>(FilmDetailState()) {

    private var fetchFilmDetailJob: Job? = null

    override fun onViewCreated(vararg input: Any?) {
        fetchFilmDetailByUrl(input.getValueAt(0) as String)
    }

    override fun onChangeState(action: FilmDetailAction): FilmDetailState {
        return when (action) {
            is FilmDetailAction.UpdateFilmDetail -> state.copy(
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
                        performAction(FilmDetailAction.FilmDetailLoadError("Unable to load film details"))
                    },
                    {
                        emit(FilmDetailAction.UpdateFilmDetail(film = it))
                    }
                )
            }
        )
    }
}