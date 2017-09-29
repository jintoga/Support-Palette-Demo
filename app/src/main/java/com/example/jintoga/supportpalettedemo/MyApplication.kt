package com.example.jintoga.supportpalettedemo

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory
import com.facebook.imagepipeline.listener.RequestListener
import com.facebook.imagepipeline.listener.RequestLoggingListener
import okhttp3.OkHttpClient

/**
 * Created by jintoga on 29/09/2017.
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        //Init Fresco
        val requestListeners = HashSet<RequestLoggingListener>()
        requestListeners.add(RequestLoggingListener())
        val okHttpClient = OkHttpClient()
                .newBuilder()
                .build()
        val config = OkHttpImagePipelineConfigFactory.newBuilder(applicationContext, okHttpClient)
                .setDownsampleEnabled(true)
                .setRequestListeners(requestListeners as Set<RequestListener>)
                .build()
        Fresco.initialize(applicationContext, config)
    }
}