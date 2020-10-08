package com.ldaca.app.prueba1.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import java.lang.Exception

class SplashActivity : AppCompatActivity() {
    private val TAG: String = "SplashActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            SystemClock.sleep(10000)
        } catch (e: Exception) {
            Log.w(TAG, "Error en el retardo")
        }
        val intent = Intent(this@SplashActivity, PreMainActivity::class.java)
        startActivity(intent)
        finish()
    }
}