package com.evelyn.healthpocket

object ApiClient {
    private const val BASE_URL = "https://healthpocket-api-e5e8frc8cfhdhjby.westcentralus-01.azurewebsites.net/"
    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}