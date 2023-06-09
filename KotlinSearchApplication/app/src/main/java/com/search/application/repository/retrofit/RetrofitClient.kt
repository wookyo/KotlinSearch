package com.v1.application.repository.retrofit


import com.search.application.BuildConfig
import com.v1.application.constant.APIConstant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val retrofitClient: Retrofit.Builder by lazy {

        val levelType: Level
        if (BuildConfig.BUILD_TYPE.contentEquals("debug"))
            levelType = Level.BODY else levelType = Level.NONE

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(levelType)

        val okhttpClient = OkHttpClient.Builder()
        okhttpClient.addInterceptor(interceptor)

        Retrofit.Builder()
            .baseUrl(APIConstant.BASE_URL_MAIN_LIST)
            .client(okhttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiInterface: RetrofitApiInterface by lazy {
        retrofitClient
            .build()
            .create(RetrofitApiInterface::class.java)
    }
}
