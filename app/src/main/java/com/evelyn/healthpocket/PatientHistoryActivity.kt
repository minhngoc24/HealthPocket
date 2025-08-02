package com.evelyn.healthpocket

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.patient_history) // or whatever XML filename you saved

        // Back button
        val backButton = findViewById<ImageView>(R.id.back_button)
        backButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        // Download button (reloads the page)
        val downloadButton = findViewById<Button>(R.id.download_button)
        downloadButton.setOnClickListener {
            val intent = intent
            finish()
            startActivity(intent)
        }
    }
}
