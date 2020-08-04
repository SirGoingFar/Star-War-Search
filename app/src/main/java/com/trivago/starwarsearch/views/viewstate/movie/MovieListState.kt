package com.trivago.starwarsearch.views.viewstate.movie

import com.trivago.starwarsearch.domain.dto.common.ListItem
import com.trivago.starwarsearch.views.viewstate.BaseViewState

data class MovieListState(val list: List<ListItem> = emptyList()) : BaseViewState