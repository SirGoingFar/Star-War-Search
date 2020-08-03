package com.trivago.starwarsearch.views.viewstate.film

import com.trivago.starwarsearch.domain.dto.common.ListItem
import com.trivago.starwarsearch.views.adapter.common.ListItemAdapter
import com.trivago.starwarsearch.views.viewstate.BaseViewState

data class FilmListState(val list: List<ListItem> = emptyList()) : BaseViewState