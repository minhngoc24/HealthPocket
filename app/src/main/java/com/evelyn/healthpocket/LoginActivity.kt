package com.evelyn.healthpocket

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val signInButton = findViewById<Button>(R.id.signInButton)

        val api = ApiClient.instance.create(ApiService::class.java)

        signInButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 🔹 gọi API gửi OTP qua Gmail
            val body = mapOf("email" to email)
            api.requestOtp(body).enqueue(object : Callback<Map<String, Any>> {
                override fun onResponse(call: Call<Map<String, Any>>, response: Response<Map<String, Any>>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@LoginActivity, "OTP sent to $email", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@LoginActivity, OTPConfirmation::class.java)
                        intent.putExtra("email", email)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@LoginActivity, "Failed to send OTP", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Map<String, Any>>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    fun onForgotPassword(view: android.view.View) {
        Toast.makeText(this, "Forgot Password Clicked", Toast.LENGTH_SHORT).show()
    }

    fun onCreateAccount(view: android.view.View) {
        startActivity(Intent(this, CreateAccountActivity::class.java))
    }
}
