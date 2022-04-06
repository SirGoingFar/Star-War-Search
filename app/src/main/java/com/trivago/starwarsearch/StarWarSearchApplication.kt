package com.trivago.starwarsearch

//import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.crashreporter.CrashReporterPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.soloader.SoLoader
import com.trivago.starwarsearch.common.di.component.AppComponent
import com.trivago.starwarsearch.common.di.component.DaggerAppComponent
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump


class StarWarSearchApplication : MultiDexApplication() {

    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    init {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

        // Doing this for Android 4 MultiDex backward compatibility
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()

        //set font stylist
        initCalligraphyConfig()

        //set up debugging tools
        initDebuggingTool()
    }

    private fun initCalligraphyConfig() {
        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(
                    CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                            .setDefaultFontPath("fonts/Muli-Regular.ttf")
                            .setFontAttrId(R.attr.fontPath)
                            .build()
                    )
                )
                .build()
        )
    }

    private fun initDebuggingTool() {
        SoLoader.init(this, false)
        val enableFlipper = FlipperUtils.shouldEnableFlipper(this)
        if (BuildConfig.DEBUG && enableFlipper) {
            val client = AndroidFlipperClient.getInstance(this)
            client.addPlugin(InspectorFlipperPlugin(this, DescriptorMapping.withDefaults()))
            client.addPlugin(CrashReporterPlugin.getInstance())
            client.start()
        }
    }

    private fun stopDebuggingTool() {
        if (!BuildConfig.DEBUG) {
            return
        }

        AndroidFlipperClient.getInstanceIfInitialized()?.stop()
    }


    private fun initializeComponent(): AppComponent {
        return DaggerAppComponent.factory().create(applicationContext)
    }

}