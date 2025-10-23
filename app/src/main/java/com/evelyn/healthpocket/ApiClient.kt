package com.evelyn.healthpocket

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://healthpocket-api-e5e8frc8cfhdhjby.westcentralus-01.azurewebsites.net/"
<<<<<<< HEAD
//    private const val BASE_URL = "http://10.0.2.2:3000/"

=======
>>>>>>> eb0dbd8d11a40cd9ff4fccc1721f822358895122

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)   
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
