package com.trivago.starwarsearch.presentation.viewmodel.species

import com.trivago.starwarsearch.common.core.extension.cancelIfActive
import com.trivago.starwarsearch.common.core.extension.getValueAt
import com.trivago.starwarsearch.domain.usecase.species.FetchPlanetDetailByUrl
import com.trivago.starwarsearch.domain.usecase.species.FetchSpecieDetailByUrl
import com.trivago.starwarsearch.presentation.viewaction.species.SpecieDetailAction
import com.trivago.starwarsearch.presentation.viewmodel.BaseViewModel
import com.trivago.starwarsearch.presentation.viewstate.species.SpecieDetailState
import kotlinx.coroutines.Job
import javax.inject.Inject

class SpecieDetailViewModel @Inject constructor(
    private val fetchSpecieDetailByUrl: FetchSpecieDetailByUrl,
    private val fetchPlanetDetailByUrl: FetchPlanetDetailByUrl
) : BaseViewModel<SpecieDetailState, SpecieDetailAction>(
    SpecieDetailState()
) {

    private var fetchSpecieDetailJob: Job? = null
    private var fetchPlanetDetailJob: Job? = null

    override fun onViewCreated(vararg input: Any?) {
        fetchSpecieDetailByUrl(input.getValueAt(0) as String)
    }

    override fun onChangeState(action: SpecieDetailAction): SpecieDetailState {
        return when (action) {
            is SpecieDetailAction.SpecieDetailFetchUnsuccessful -> state.copy(
                errorMsg = action.msg,
                showLoader = false,
                specie = null
            )
            is SpecieDetailAction.UpdateSpecielDetail -> state.copy(
                errorMsg = null,
                showLoader = false,
                specie = action.specie
            )
            is SpecieDetailAction.UpdatePlanetDetail -> state.copy(
                specie = state.specie!!.copy(
                    homeWorldName = action.name,
                    population = action.population
                )
            )
            else -> state
        }
    }

    private fun fetchSpecieDetailByUrl(url: String) {
        fetchSpecieDetailJob?.cancelIfActive()
        fetchSpecieDetailJob = doJobWithDispatcher(
            job = {
                fetchSpecieDetailByUrl.execute(url).fold(
                    {
                        emit(SpecieDetailAction.SpecieDetailFetchUnsuccessful("Unable to fetch specie detail"))
                    },
                    {
                        if (it.population.isNullOrEmpty() || it.homeWorldName.isNullOrEmpty())
                            fetchPlanetDetail(url, it.homeWorld)
                        emit(SpecieDetailAction.UpdateSpecielDetail(it))
                    }
                )
            }
        )
    }

    private fun fetchPlanetDetail(specielUrl: String, homeWorldUrl: String) {
        fetchPlanetDetailJob?.cancelIfActive()
        fetchPlanetDetailJob = doJobWithDispatcher(
            job = {
                fetchPlanetDetailByUrl.execute(listOf(specielUrl, homeWorldUrl)).fold({},
                    {
                        emit(SpecieDetailAction.UpdatePlanetDetail(it.population, it.name))
                    }
                )
            }
        )
    }
}