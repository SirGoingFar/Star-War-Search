package com.trivago.starwarsearch.presentation.viewmodel.species

import com.trivago.starwarsearch.common.core.extension.getValueAt
import com.trivago.starwarsearch.domain.usecase.species.FetchSpecieItemsByCharacterUrl
import com.trivago.starwarsearch.presentation.viewaction.species.SpeciesListAction
import com.trivago.starwarsearch.presentation.viewmodel.BaseViewModel
import com.trivago.starwarsearch.presentation.viewstate.species.SpeciesListState
import javax.inject.Inject

class SpeciesListViewModel @Inject constructor(
    private val fetchSpecieItemsByUrl: FetchSpecieItemsByCharacterUrl
) : BaseViewModel<SpeciesListState, SpeciesListAction>(
    SpeciesListState()
) {

    override fun onViewCreated(vararg input: Any?) {
        fetchFilmItems((input.getValueAt(0)) as String)
    }

    override fun onChangeState(action: SpeciesListAction): SpeciesListState {
        return when (action) {
            is SpeciesListAction.UpdateSpecieList -> state.copy(list = action.list)
        }
    }

    private fun fetchFilmItems(url: String) {
        doJobWithDispatcher(job = {
            fetchSpecieItemsByUrl.execute(url).fold({},
                {
                    emit(SpeciesListAction.UpdateSpecieList(it))
                }
            )
        })
    }

}