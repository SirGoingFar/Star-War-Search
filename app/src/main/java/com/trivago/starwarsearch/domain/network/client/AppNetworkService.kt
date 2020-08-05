package com.trivago.starwarsearch.domain.network.client

interface AppNetworkService {

    fun getCharacterApi():CharacterApi

    fun getMovieApi():MovieApi

    fun getSpecieApi():SpecieApi

    fun getPlanetApi():PlanetApi

}