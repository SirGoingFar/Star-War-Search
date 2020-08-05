package com.trivago.starwarsearch.domain.datasource.movie

import com.trivago.starwarsearch.domain.datasource.character.FakeCharacterLocalSource
import com.trivago.starwarsearch.domain.datasource.character.FakeCharacterRemoteSource
import com.trivago.starwarsearch.domain.datasource.specie.data.CharacterDataUtil
import com.trivago.starwarsearch.domain.repository.character.FakeCharacterRepository

class FakeMovieLocalSource : MovieLocalSource(
    FakeCharacterRepository(
        FakeCharacterLocalSource(),
        FakeCharacterRemoteSource(CharacterDataUtil.searchCharacterResponse)
    )
)