package com.ldaca.app.prueba1.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.SystemClock
import android.util.Log


class LicenciaService : Service() {
    private val TAG: String = "SplashActivity"
    private var timer: ShowThread? = null

    companion object {
        fun getInstance() {

        }
    }

    override fun onCreate() {
        super.onCreate()
        timer = ShowThread()
    }

    override fun onBind(p0: Intent?): IBinder? {
        throw UnsupportedOperationException("No se ha implementado todavia")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        timer?.start()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.interrupt()
        timer = null
    }


    private inner class ShowThread() :Thread() {
        private var count = 0
        override fun run() {
            try {
                SystemClock.sleep(10000)
            } catch (e: Exception) {
                Log.w(TAG, "Error en el retardo")
            }
            count++
//            Toast.makeText(this@LicenciaService, "Esto es una prueba", Toast.LENGTH_SHORT).show()


//            runOnUiThread {
//                val toast = Toast.makeText(this@LicenciaService, "GAME OVER!", Toast.LENGTH_SHORT)
//                toast.show()
//            }

            if (count == 2) stopSelf()
        }
    }
}