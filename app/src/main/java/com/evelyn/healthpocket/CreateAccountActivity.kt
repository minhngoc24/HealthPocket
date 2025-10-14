package com.evelyn.healthpocket

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.evelyn.healthpocket.databinding.CreateAccountBinding
import com.evelyn.healthpocket.ApiClient
import com.evelyn.healthpocket.ApiService
import retrofit2.*


class CreateAccountActivity : AppCompatActivity() {

    private lateinit var binding: CreateAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.signInButton.text = "Create Account"

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

    /** Meo meo */
    private fun submitCreateAccount() {
        val phone = binding.phoneEditText.text.toString().trim()
        val password = binding.PasswordEditText.text.toString().trim()
        val confirmPassword = binding.PasswordConfirmEditText.text.toString().trim()

        when {
            phone.isEmpty() -> {
                binding.phoneEditText.error = "Please enter phone number"
                binding.phoneEditText.requestFocus()
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
        val body = mapOf("email" to phone) // or "phone" if your backend expects that

        api.requestOtp(body).enqueue(object : retrofit2.Callback<Map<String, Any>> {
            override fun onResponse(
                call: retrofit2.Call<Map<String, Any>>,
                response: retrofit2.Response<Map<String, Any>>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@CreateAccountActivity,
                        "OTP sent! Check your email or phone.",
                        Toast.LENGTH_SHORT
                    ).show()

                    // Move to verify OTP screen
                    val intent = Intent(this@CreateAccountActivity, OTPConfirmation::class.java)
                    intent.putExtra("email", phone)
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this@CreateAccountActivity,
                        "Failed to send OTP: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: retrofit2.Call<Map<String, Any>>, t: Throwable) {
                Toast.makeText(
                    this@CreateAccountActivity,
                    "Network error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }


    /** gắn với TextView android:onClick="onCreateAccount" trong XML */
    fun onCreateAccount(@Suppress("UNUSED_PARAMETER") view: View) {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}



