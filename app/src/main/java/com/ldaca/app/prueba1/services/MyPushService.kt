package com.ldaca.app.prueba1.services

import android.content.Intent
import android.util.Log
import com.huawei.hms.push.HmsMessageService


class MyPushService: HmsMessageService() {
    val TAG = "MyPushService - MainActivity"

    override fun onNewToken(token: String?) {
        super.onNewToken(token)
        Log.i(TAG, "receive token:$token");
        sendTokenToDisplay(token!!);
    }

    private fun sendTokenToDisplay(token: String) {
        val intent = Intent("com.huawei.push.codelab.ON_NEW_TOKEN")
        intent.putExtra("token", token)
        sendBroadcast(intent)
    }
}
