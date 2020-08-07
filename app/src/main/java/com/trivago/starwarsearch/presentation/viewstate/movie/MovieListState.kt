package com.trivago.starwarsearch.presentation.viewstate.movie

import com.trivago.starwarsearch.data.dto.common.ListItem
import com.trivago.starwarsearch.presentation.viewstate.BaseViewState

data class MovieListState(val list: List<ListItem> = emptyList()) : BaseViewState