package com.trivago.starwarsearch.views.viewmodel.character_detail

import com.trivago.starwarsearch.core.extension.getValueAt
import com.trivago.starwarsearch.domain.usecase.character_search.FetchCharacterByUrl
import com.trivago.starwarsearch.views.util.formatHeight
import com.trivago.starwarsearch.views.viewaction.character_detail.CharacterDetailAction
import com.trivago.starwarsearch.views.viewmodel.BaseViewModel
import com.trivago.starwarsearch.views.viewstate.character_detail.CharacterDetailState
import javax.inject.Inject

class CharacterDetailViewModel @Inject constructor(
    private val fetchCharacterByUrl: FetchCharacterByUrl
) : BaseViewModel<CharacterDetailState, CharacterDetailAction>(
    CharacterDetailState()
) {

    override fun onLoadData(vararg input: Any?) {
        fetchCharacterWithUrl(input.getValueAt(0) as String)
    }

    override fun onChangeState(action: CharacterDetailAction): CharacterDetailState {
        return when (action) {
            is CharacterDetailAction.SetCharacterDetail -> state.copy(
                url = action.url,
                name = action.name,
                birthYear = action.birthYear,
                gender = action.gender,
                seeMoreText = action.seeMoreText,
                height = action.height,
                hasFilm = action.hasFilm,
                hasSpecies = action.hasSpecies
            )
        }
    }

    private fun fetchCharacterWithUrl(characterUrl: String) {
        doJobWithDispatcher(
            job = {
                fetchCharacterByUrl.execute(characterUrl).fold({}, {
                    emit(
                        CharacterDetailAction.SetCharacterDetail(
                            url = it.url,
                            name = it.name,
                            birthYear = it.birthYear,
                            gender = it.gender.capitalize(),
                            height = formatHeight(it.height),
                            seeMoreText = "See more about ${it.name}:",
                            hasFilm = it.films.isNotEmpty(),
                            hasSpecies = it.species.isNotEmpty()
                        )
                    )
                })
            }
        )
    }
}