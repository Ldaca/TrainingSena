package com.ldaca.app.prueba1.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.lang.UnsupportedOperationException

class SoatService: Service() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun onBind(p0: Intent?): IBinder? {
        throw UnsupportedOperationException("No se ha implementado todavia")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}