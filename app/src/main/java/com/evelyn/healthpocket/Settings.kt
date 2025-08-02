package com.evelyn.healthpocket

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Settings : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)

        // Account section
        val editProfile = findViewById<LinearLayout>(R.id.edit_profile)
        val privacy = findViewById<LinearLayout>(R.id.privacy)
        val notifications = findViewById<LinearLayout>(R.id.notifications)

        // Actions section
        val help = findViewById<LinearLayout>(R.id.help)
        // val settings = findViewById<LinearLayout>(R.id.settings)
        val logout = findViewById<LinearLayout>(R.id.logout)

        // Set click listeners
        editProfile.setOnClickListener {
            startActivity(Intent(this, EditProfileActivity::class.java))
        }

        privacy.setOnClickListener {
            startActivity(Intent(this, PrivacyActivity::class.java))
        }

        notifications.setOnClickListener {
            startActivity(Intent(this, NotificationsActivity::class.java))
        }

        help.setOnClickListener {
            startActivity(Intent(this, HelpActivity::class.java))
        }


        logout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }

}