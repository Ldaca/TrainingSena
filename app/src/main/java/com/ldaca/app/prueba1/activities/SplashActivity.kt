package com.ldaca.app.prueba1.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import java.lang.Exception

class SplashActivity : AppCompatActivity() {
    private val TAG: String = "SplashActivity"
    private lateinit var preferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferences = getSharedPreferences("Preferencias", Context.MODE_PRIVATE)

        try {
            Thread.sleep(1000)
        } catch (e: Exception) {
            Thread.currentThread().interrupt()
        }

        if (isThereProfile()) {
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this@SplashActivity, PreMainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun isThereProfile() = preferences.getBoolean("exist", false)
}