package com.evelyn.healthpocket

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.evelyn.healthpocket.databinding.CreateAccountBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateAccountActivity : AppCompatActivity() {

    private lateinit var binding: CreateAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Đổi text nút để người dùng hiểu
        binding.signInButton.text = "Create Account"

        // Gửi OTP khi nhấn nút
        binding.signInButton.setOnClickListener {
            hideKeyboard()
            submitCreateAccount()
        }
    }

    private fun hideKeyboard() {
        currentFocus?.let { v ->
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }

    /** Gửi request OTP tạo tài khoản */
    private fun submitCreateAccount() {
        val email = binding.emailEditText.text.toString().trim()
        val password = binding.PasswordEditText.text.toString().trim()
        val confirmPassword = binding.PasswordConfirmEditText.text.toString().trim()

        when {
            email.isEmpty() -> {
                binding.emailEditText.error = "Please enter your email"
                binding.emailEditText.requestFocus()
                return
            }
            password.isEmpty() -> {
                binding.PasswordEditText.error = "Please enter password"
                binding.PasswordEditText.requestFocus()
                return
            }
            confirmPassword != password -> {
                binding.PasswordConfirmEditText.error = "Passwords do not match"
                binding.PasswordConfirmEditText.requestFocus()
                return
            }
        }

        val api = ApiClient.instance.create(ApiService::class.java)
        val body = mapOf("email" to email)

        api.requestOtp(body).enqueue(object : Callback<Map<String, Any>> {
            override fun onResponse(
                call: Call<Map<String, Any>>,
                response: Response<Map<String, Any>>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@CreateAccountActivity,
                        "OTP sent! Check your email to verify your account.",
                        Toast.LENGTH_SHORT
                    ).show()

                    // 👉 Chuyển sang màn OTPConfirmation
                    val intent = Intent(this@CreateAccountActivity, OTPConfirmation::class.java)
                    intent.putExtra("email", email)
                    intent.putExtra("password", password)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(
                        this@CreateAccountActivity,
                        "Failed to send OTP: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<Map<String, Any>>, t: Throwable) {
                Toast.makeText(
                    this@CreateAccountActivity,
                    "Network error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    /** TextView "Create an account" → quay lại LoginActivity */
    fun onCreateAccount(@Suppress("UNUSED_PARAMETER") view: View) {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
