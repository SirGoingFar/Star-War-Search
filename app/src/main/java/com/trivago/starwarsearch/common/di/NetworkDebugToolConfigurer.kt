package com.trivago.starwarsearch.common.di

import okhttp3.OkHttpClient

interface NetworkDebugToolConfigurer {
    fun configure(okHttpBuilder: OkHttpClient.Builder)
}