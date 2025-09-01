package com.evelyn.healthpocket

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.evelyn.healthpocket.databinding.SelectRoleBinding

class SelectRoleBinding : AppCompatActivity() {

    private lateinit var binding: SelectRoleBinding
    private var firstTrigger = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SelectRoleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // dữ liệu cho Spinner
        val roles = listOf("Select a role", "Patient", "Doctor")

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            roles
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.roleSpinner.adapter = adapter

        binding.roleSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (firstTrigger) { firstTrigger = false; return }
                if (position == 0) return

                when (roles[position]) {
                    "Patient" -> startActivity(
                        Intent(this@SelectRoleBinding, PatientProfileActivity::class.java)
                    )
                    "Doctor" -> startActivity(
                        Intent(this@SelectRoleBinding, DoctorPatientProfileActivity::class.java)
                    )

                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }
}


