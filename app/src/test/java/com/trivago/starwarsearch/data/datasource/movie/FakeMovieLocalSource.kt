package com.trivago.starwarsearch.data.datasource.movie

import com.trivago.starwarsearch.data.datasource.character.FakeCharacterLocalSource
import com.trivago.starwarsearch.data.datasource.character.FakeCharacterRemoteSource
import com.trivago.starwarsearch.data.datasource.specie.data.CharacterDataUtil
import com.trivago.starwarsearch.data.repository.character.FakeCharacterRepository

class FakeMovieLocalSource : MovieLocalSource(
    FakeCharacterRepository(
        FakeCharacterLocalSource(),
        FakeCharacterRemoteSource(CharacterDataUtil.searchCharacterResponse)
    )
)