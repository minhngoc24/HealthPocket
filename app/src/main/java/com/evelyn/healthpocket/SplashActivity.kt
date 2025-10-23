package com.evelyn.healthpocket

import android.content.Intent // Use to switch between activities
import android.content.SharedPreferences // Use to store key value (login status)
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

// Use AppCompatActivity for compatibility on old android versions
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check if the user is already logged in
        val sharePref = getSharedPreferences("Username", MODE_PRIVATE)
        val isLogin = sharePref.getBoolean("IsLogin", false) // retrieve login status, default false if not found

        if(isLogin){
            startActivity(Intent(this, DashBoardActivity::class.java))
        }
        else{
            startActivity(Intent(this, TitlePageActivity::class.java))
        }
        finish()
    }
}
/////