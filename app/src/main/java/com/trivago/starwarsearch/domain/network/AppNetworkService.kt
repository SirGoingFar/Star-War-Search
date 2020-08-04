package com.trivago.starwarsearch.domain.network

import com.trivago.starwarsearch.domain.network.client.CharacterApi
import com.trivago.starwarsearch.domain.network.client.MovieApi
import com.trivago.starwarsearch.domain.network.client.PlanetApi
import com.trivago.starwarsearch.domain.network.client.SpecieApi
import retrofit2.Retrofit
import javax.inject.Inject

class AppNetworkService @Inject constructor(retrofit: Retrofit) {

    val characterApi: CharacterApi = retrofit.create(CharacterApi::class.java)

    val movieApi: MovieApi = retrofit.create(MovieApi::class.java)

    val specieApi: SpecieApi = retrofit.create(SpecieApi::class.java)

    val planetApi: PlanetApi = retrofit.create(PlanetApi::class.java)

}