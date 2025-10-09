package com.evelyn.healthpocket

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.evelyn.healthpocket.ui.theme.HealthPocketTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        val api = ApiClient.instance.create(ApiService::class.java)

        api.checkHealth().enqueue(object : retrofit2.Callback<Map<String, Boolean>> {
            override fun onResponse(
                call: retrofit2.Call<Map<String, Boolean>>,
                response: retrofit2.Response<Map<String, Boolean>>
            ) {
                android.util.Log.d("API", "Response code: ${response.code()} | body: ${response.body()}")
            }

            override fun onFailure(call: retrofit2.Call<Map<String, Boolean>>, t: Throwable) {
                android.util.Log.e("API", "Error: ${t.message}")
            }
        })

        setContent {
            HealthPocketTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "HealthPocket",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(text = "Hello $name!", modifier = modifier)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HealthPocketTheme {
        Greeting("Android")
    }
}
