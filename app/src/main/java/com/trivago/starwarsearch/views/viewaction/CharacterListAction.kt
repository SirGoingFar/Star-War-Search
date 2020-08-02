package com.trivago.starwarsearch.views.viewaction

import com.trivago.starwarsearch.domain.dto.Character

sealed class CharacterListAction : BaseAction {

    class RefreshCharacterList(val newList: List<Character>) : CharacterListAction()

    class ToggleLoader(val show: Boolean) : CharacterListAction()

    class ShowEmptyState(val title: String, val body: String) : CharacterListAction()

    class SetQueryText(val text: String) : CharacterListAction()

}