package com.ducnd.realmmvp.remote.interact.main

import com.ducnd.realmmvp.BuildConfig
import com.ducnd.realmmvp.remote.interact.source.IBaseApiConnector
import com.ducnd.realmmvp.utils.Constants
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by ducnd on 8/9/17.
 */

abstract class BaseApiConnector<Api> protected constructor(endpoint: String, mClazz: Class<Api>) : IBaseApiConnector {
    protected val mApi: Api

    init {
        mApi = createRetrofit(endpoint).create(mClazz)
    }

    private fun createRetrofit(endpoint: String): Retrofit {
        return Retrofit.Builder()
                .baseUrl(endpoint)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(createOkHttp())
                .build()
    }

    private fun createOkHttp(): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.addInterceptor(LoggingInterceptor.Builder()
                .loggable(Constants.DEBUG)
                .setLevel(Level.BASIC)
                .log(Platform.INFO)
                .request("Request")
                .response("Response")
                .addHeader("version", BuildConfig.VERSION_NAME)
                .build())
        return client
                .retryOnConnectionFailure(true)
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .build()
    }


}
