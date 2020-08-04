package com.trivago.starwarsearch.views.viewaction.movie

import com.trivago.starwarsearch.domain.dto.common.ListItem
import com.trivago.starwarsearch.views.viewaction.BaseAction

sealed class MovieListViewAction : BaseAction {
    class UpdateFilmList(val list: List<ListItem>) : MovieListViewAction()
}