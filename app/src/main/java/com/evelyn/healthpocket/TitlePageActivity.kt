package com.evelyn.healthpocket

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TitlePageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.titlepage)

        // ✅ Gọi API checkHealth từ Azure để test
        val api = ApiClient.instance
        Log.d("API", "Calling Azure health check from TitlePageActivity...")

        api.checkHealth().enqueue(object : Callback<Map<String, Boolean>> {
            override fun onResponse(
                call: Call<Map<String, Boolean>>,
                response: Response<Map<String, Boolean>>
            ) {
                Log.d("API", "Response code: ${response.code()} | body: ${response.body()}")
            }

            override fun onFailure(call: Call<Map<String, Boolean>>, t: Throwable) {
                Log.e("API", "Error calling Azure: ${t.message}")
            }
        })

        // Splash delay như cũ
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 2000)
    }
}
