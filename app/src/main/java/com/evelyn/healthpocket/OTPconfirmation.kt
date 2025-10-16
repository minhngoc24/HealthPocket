package com.evelyn.healthpocket

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OTPConfirmation : AppCompatActivity() {

    private lateinit var otp1: EditText
    private lateinit var otp2: EditText
    private lateinit var otp3: EditText
    private lateinit var otp4: EditText
    private lateinit var otp5: EditText
    private lateinit var otp6: EditText
    private lateinit var userEmail: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.otp_comfirmation)

        // ✅ Nhận email từ LoginActivity
        userEmail = intent.getStringExtra("email") ?: ""

        otp1 = findViewById(R.id.otp1)
        otp2 = findViewById(R.id.otp2)
        otp3 = findViewById(R.id.otp3)
        otp4 = findViewById(R.id.otp4)
        otp5 = findViewById(R.id.otp5)
        otp6 = findViewById(R.id.otp6)

        setupOtpInputs()
    }

    private fun setupOtpInputs() {
        val fields = listOf(otp1, otp2, otp3, otp4, otp5, otp6)

        fields.forEachIndexed { i, et ->
            et.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    if (s?.length == 1 && i < fields.lastIndex) fields[i + 1].requestFocus()
                    if (s?.isEmpty() == true && i > 0) fields[i - 1].requestFocus()

                    // Khi đủ 6 ký tự OTP
                    if (fields.all { it.text.length == 1 }) {
                        val code = fields.joinToString("") { it.text.toString() }
                        verifyOtpWithApi(code)
                    }
                }
            })
        }
    }

    private fun verifyOtpWithApi(enteredOtp: String) {
        if (userEmail.isEmpty()) {
            Toast.makeText(this, "Email missing — please login again.", Toast.LENGTH_SHORT).show()
            return
        }

        val api = ApiClient.instance.create(ApiService::class.java)
        val body = mapOf("email" to userEmail, "otp" to enteredOtp)

        api.verifyOtp(body).enqueue(object : Callback<Map<String, Any>> {
            override fun onResponse(call: Call<Map<String, Any>>, response: Response<Map<String, Any>>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@OTPConfirmation, "✅ OTP verified successfully!", Toast.LENGTH_SHORT).show()
                    // TODO: startActivity(Intent(this@OTPConfirmation, MainActivity::class.java))
                } else {
                    Toast.makeText(this@OTPConfirmation, "❌ Invalid OTP", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Map<String, Any>>, t: Throwable) {
                Toast.makeText(this@OTPConfirmation, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
