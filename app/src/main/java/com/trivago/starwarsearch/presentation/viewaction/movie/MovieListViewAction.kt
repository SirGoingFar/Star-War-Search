package com.trivago.starwarsearch.presentation.viewaction.movie

import com.trivago.starwarsearch.data.dto.common.ListItem
import com.trivago.starwarsearch.presentation.viewaction.BaseAction

sealed class MovieListViewAction : BaseAction {
    class UpdateFilmList(val list: List<ListItem>) : MovieListViewAction()
}