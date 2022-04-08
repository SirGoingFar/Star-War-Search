package com.trivago.starwarsearch.common.di

import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import okhttp3.OkHttpClient
import javax.inject.Inject

class NetworkDebugToolConfigurerImpl @Inject constructor() : NetworkDebugToolConfigurer {
    override fun configure(okHttpBuilder: OkHttpClient.Builder) {
        val client = AndroidFlipperClient.getInstanceIfInitialized()
        if (client?.getPluginByClass(NetworkFlipperPlugin::class.java) == null) {
            val nfp = NetworkFlipperPlugin()
            okHttpBuilder.addNetworkInterceptor(FlipperOkhttpInterceptor(nfp))
            client?.addPlugin(nfp)
        }
    }
}