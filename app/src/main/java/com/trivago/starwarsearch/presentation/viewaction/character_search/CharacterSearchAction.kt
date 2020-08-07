package com.trivago.starwarsearch.presentation.viewaction.character_search

import com.trivago.starwarsearch.data.dto.character_search.Character
import com.trivago.starwarsearch.presentation.viewaction.BaseAction

sealed class CharacterSearchAction :
    BaseAction {

    class RefreshCharacterList(val newList: List<Character>) : CharacterSearchAction()

    class ToggleLoader(val show: Boolean) : CharacterSearchAction()

    class ShowEmptyState(val title: String, val body: String) : CharacterSearchAction()

    class SetQueryText(val text: String) : CharacterSearchAction()

}