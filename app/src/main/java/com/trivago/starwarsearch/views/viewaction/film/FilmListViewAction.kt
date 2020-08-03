package com.trivago.starwarsearch.views.viewaction.film

import com.trivago.starwarsearch.domain.dto.common.ListItem
import com.trivago.starwarsearch.views.viewaction.BaseAction

sealed class FilmListViewAction : BaseAction {
    class UpdateFilmList(val list: List<ListItem>) : FilmListViewAction()
}