package com.evelyn.healthpocket

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class PatientProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.patient_view_profile) // use your actual XML filename

        // Back to Home
        val backButton = findViewById<ImageView>(R.id.back_button)
        backButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        // Reload actions
        val downloadBtn = findViewById<Button>(R.id.download_button)
        val shareBtn = findViewById<Button>(R.id.share_button)
        val requestBtn = findViewById<Button>(R.id.request_button)

        val reloadIntent = intent

        downloadBtn.setOnClickListener {
            finish()
            startActivity(reloadIntent)
        }

        shareBtn.setOnClickListener {
            finish()
            startActivity(reloadIntent)
        }

        requestBtn.setOnClickListener {
            finish()
            startActivity(reloadIntent)
        }
    }
}
