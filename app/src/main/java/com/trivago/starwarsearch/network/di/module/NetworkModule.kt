package com.trivago.starwarsearch.network.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.trivago.starwarsearch.BuildConfig
import com.trivago.starwarsearch.domain.network.DefaultAppNetworkService
import com.trivago.starwarsearch.domain.network.client.AppNetworkService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {

    @Provides
    fun providesRetrofit(): Retrofit {

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(logInterceptor())
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()


        return Retrofit.Builder()
            .baseUrl(APP_BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
    }

    @Provides
    fun providesGson(): Gson {
        return Gson()
    }

    @Provides
    fun provideAppNetworkService(defaultAppNetworkService: DefaultAppNetworkService): AppNetworkService =
        defaultAppNetworkService


    private fun logInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }
    }

    companion object {
        private const val APP_BASE_URL: String = BuildConfig.APP_BASE_URL
    }

}