package com.trivago.starwarsearch.common.network.di.module

import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.trivago.starwarsearch.BuildConfig
import com.trivago.starwarsearch.data.network.DefaultAppNetworkService
import com.trivago.starwarsearch.data.network.client.AppNetworkService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {

        val httpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)

        val adc = AndroidFlipperClient.getInstanceIfInitialized()
        if(adc?.getPluginByClass(NetworkFlipperPlugin::class.java) == null){
            val nfp = NetworkFlipperPlugin()
            httpClientBuilder.addNetworkInterceptor(FlipperOkhttpInterceptor(nfp))
            //Todo: Do this in an interactor
            AndroidFlipperClient.getInstanceIfInitialized()?.addPlugin(nfp)
        }

        val httpClient = httpClientBuilder.build()

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