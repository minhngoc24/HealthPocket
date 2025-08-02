package com.evelyn.healthpocket

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class DashBoardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard) // Use your XML file name (e.g. dashboard.xml)

        // Find the "View record" button
        val viewRecordButton = findViewById<Button>(R.id.view_record_button)

        // When clicked, go to PatientViewActivity
        viewRecordButton.setOnClickListener {
            val intent = Intent(this, PatientProfileActivity::class.java)
            startActivity(intent)
        }
    }
}
