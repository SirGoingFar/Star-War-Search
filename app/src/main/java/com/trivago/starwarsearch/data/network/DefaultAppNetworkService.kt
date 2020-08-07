package com.trivago.starwarsearch.data.network

import com.trivago.starwarsearch.data.network.client.*
import retrofit2.Retrofit
import javax.inject.Inject

class DefaultAppNetworkService @Inject constructor(private val retrofit: Retrofit):AppNetworkService {

    override fun getCharacterApi(): CharacterApi= retrofit.create(CharacterApi::class.java)

    override fun getMovieApi(): MovieApi= retrofit.create(MovieApi::class.java)

    override fun getSpecieApi(): SpecieApi = retrofit.create(SpecieApi::class.java)

    override fun getPlanetApi(): PlanetApi= retrofit.create(PlanetApi::class.java)

}