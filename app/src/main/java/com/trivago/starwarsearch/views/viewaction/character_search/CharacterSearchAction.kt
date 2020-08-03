package com.trivago.starwarsearch.views.viewaction.character_search

import com.trivago.starwarsearch.domain.dto.Character
import com.trivago.starwarsearch.views.viewaction.BaseAction

sealed class CharacterSearchAction :
    BaseAction {

    class RefreshCharacterList(val newList: List<Character>) : CharacterSearchAction()

    class ToggleLoader(val show: Boolean) : CharacterSearchAction()

    class ShowEmptyState(val title: String, val body: String) : CharacterSearchAction()

    class SetQueryText(val text: String) : CharacterSearchAction()

}