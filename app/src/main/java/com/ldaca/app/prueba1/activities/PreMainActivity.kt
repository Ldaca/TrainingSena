package com.ldaca.app.prueba1.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.huawei.hms.support.hwid.HuaweiIdAuthManager
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParams
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParamsHelper
import com.huawei.hms.support.hwid.service.HuaweiIdAuthService
import com.ldaca.app.prueba1.databinding.ActivityMainPreBinding

class PreMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainPreBinding
//    private val authParams : HuaweiIdAuthParams = HuaweiIdAuthParamsHelper(HuaweiIdAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
//        .setIdToken().createParams()
//    private val service : HuaweiIdAuthService = HuaweiIdAuthManager.getService(this, authParams)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainPreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.signUp.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.sign1.setOnClickListener {
//            startActivityForResult(service.signInIntent, 8888)
        }
    }
}