package com.evelyn.healthpocket

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface ApiService {
    @GET("health")
    fun checkHealth(): Call<Map<String, Boolean>>

    @POST("api/request-otp")
    fun requestOtp(@Body body: Map<String, String>): Call<Map<String, Any>>

    @POST("api/verify-otp")
    fun verifyOtp(@Body body: Map<String, String>): Call<Map<String, Any>>
}
