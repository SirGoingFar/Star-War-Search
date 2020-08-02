package com.trivago.starwarsearch.domain.network

import com.trivago.starwarsearch.domain.network.client.CharacterApi
import retrofit2.Retrofit
import javax.inject.Inject

class CharacterNetworkService @Inject constructor(retrofit: Retrofit) {
    val characterApi = retrofit.create(CharacterApi::class.java)
}