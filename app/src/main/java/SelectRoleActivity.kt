package com.evelyn.healthpocket

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.evelyn.healthpocket.databinding.ActivitySelectRoleBinding

class SelectRoleActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectRoleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectRoleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Spinner data
        val roles = listOf("Select a role", "Patient", "Doctor", "Hospital")
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            roles
        )
        binding.roleSpinner.adapter = adapter

        // Spinner listener
        binding.roleSpinner.setOnItemSelectedListener { _, _, position, _ ->
            if (position != 0) { // Skip the first hint item
                val selectedRole = roles[position]
                Toast.makeText(this, "Selected: $selectedRole", Toast.LENGTH_SHORT).show()

                // Navigate based on role
                when (selectedRole) {
                    "Patient" -> {
                        startActivity(Intent(this, PatientLoginActivity::class.java))
                    }
                    "Doctor" -> {
                        startActivity(Intent(this, DoctorLoginActivity::class.java))
                    }
                    "Hospital" -> {
                        startActivity(Intent(this, HospitalLoginActivity::class.java))
                    }
                }
            }
        }
    }

    // Extension function for cleaner spinner handling
    private fun <T> android.widget.Spinner.setOnItemSelectedListener(
        onItemSelected: (parent: android.widget.AdapterView<*>, view: android.view.View?, position: Int, id: Long) -> Unit
    ) {
        this.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: android.widget.AdapterView<*>,
                view: android.view.View?,
                position: Int,
                id: Long
            ) {
                onItemSelected(parent, view, position, id)
            }

            override fun onNothingSelected(parent: android.widget.AdapterView<*>) {}
        }
    }
}
