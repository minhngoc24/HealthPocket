package com.evelyn.healthpocket

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val phoneEditText = findViewById<EditText>(R.id.phoneEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val signInButton = findViewById<Button>(R.id.signInButton)

        signInButton.setOnClickListener {
            val phone = phoneEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (phone.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please enter phone and password", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Logging in with $phone", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun onForgotPassword(view: View) {
        Toast.makeText(this, "Forgot Password Clicked", Toast.LENGTH_SHORT).show()

    }

    fun onCreateAccount(view: View) {
        startActivity(Intent(this, CreateAccountActivity::class.java))
    }
}
