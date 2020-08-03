package com.trivago.starwarsearch.views.viewmodel.film

import com.trivago.starwarsearch.core.extension.getValueAt
import com.trivago.starwarsearch.domain.usecase.film.FetchFilmItemsByCharacterUrl
import com.trivago.starwarsearch.views.viewaction.film.FilmListViewAction
import com.trivago.starwarsearch.views.viewmodel.BaseViewModel
import com.trivago.starwarsearch.views.viewstate.film.FilmListState
import javax.inject.Inject

class FilmListViewModel @Inject constructor(
    private val fetchFilmItemsByCharacterUrl: FetchFilmItemsByCharacterUrl
) : BaseViewModel<FilmListState, FilmListViewAction>(FilmListState()) {

    override fun onViewCreated(vararg input: Any?) {
        fetchFilmItems((input.getValueAt(0)) as String)
    }

    override fun onChangeState(action: FilmListViewAction): FilmListState {
        return when (action) {
            is FilmListViewAction.UpdateFilmList -> state.copy(list = action.list)
        }
    }

    private fun fetchFilmItems(url: String) {
        doJobWithDispatcher(job = {
            fetchFilmItemsByCharacterUrl.execute(url).fold({},
                {
                    emit(FilmListViewAction.UpdateFilmList(it))
                }
            )
        })
    }

}