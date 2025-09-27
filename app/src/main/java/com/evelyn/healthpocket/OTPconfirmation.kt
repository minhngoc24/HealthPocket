package com.evelyn.healthpocket

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class OTPConfirmation : AppCompatActivity() {

    private lateinit var otp1: EditText
    private lateinit var otp2: EditText
    private lateinit var otp3: EditText
    private lateinit var otp4: EditText
    private lateinit var otp5: EditText
    private lateinit var otp6: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.otp_comfirmation)

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

                    if (fields.all { it.text.length == 1 }) {
                        val code = fields.joinToString("") { it.text.toString() }
                        Toast.makeText(this@OTPConfirmation, "OTP: $code", Toast.LENGTH_SHORT).show()
                        // TODO: gọi API verify OTP ở đây
                    }
                }
            })
        }
    }
}
