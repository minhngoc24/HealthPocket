package com.evelyn.healthpocket

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.evelyn.healthpocket.databinding.CreateAccountBinding

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

    /** Xử lý tạo account */
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


        Toast.makeText(this, "Account created for $phone", Toast.LENGTH_SHORT).show()

        // quay lại màn login
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    /** gắn với TextView android:onClick="onCreateAccount" trong XML */
    fun onCreateAccount(@Suppress("UNUSED_PARAMETER") view: View) {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}


