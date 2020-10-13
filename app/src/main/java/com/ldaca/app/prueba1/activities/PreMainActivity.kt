package com.ldaca.app.prueba1.activities

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.huawei.agconnect.config.AGConnectServicesConfig
import com.huawei.hms.aaid.HmsInstanceId
import com.huawei.hms.common.ApiException
import com.huawei.hms.support.hwid.HuaweiIdAuthManager
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParams
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParamsHelper
import com.huawei.hms.support.hwid.service.HuaweiIdAuthService
import com.ldaca.app.prueba1.databinding.ActivityMainPreBinding

class PreMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainPreBinding
    private lateinit var preferences : SharedPreferences

    private val authParams : HuaweiIdAuthParams by lazy { HuaweiIdAuthParamsHelper(HuaweiIdAuthParams.DEFAULT_AUTH_REQUEST_PARAM).setIdToken().createParams() }

    private val service : HuaweiIdAuthService by lazy { HuaweiIdAuthManager.getService(this@PreMainActivity, authParams) }

    private lateinit var db:sqlite
    lateinit var colores: ArrayList<String>
    lateinit var ciudades: ArrayList<String>
    lateinit var marcas: ArrayList<String>
    private val TAG: String = "PreMainActivity"
    private var push = "No definidad"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainPreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = getSharedPreferences("Preferencias", Context.MODE_PRIVATE)

        binding.signUp.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.sign1.setOnClickListener {
            startActivityForResult(service.signInIntent, 8888)
        }

        binding.sign3.setOnClickListener {
            val signOutTask = service.signOut()
            signOutTask.addOnCompleteListener {
                // Processing after the sign-out.
                Toast.makeText(this ,"Sesión cerrada", Toast.LENGTH_SHORT).show()
                Log.i(TAG, "signOut complete")
            }
        }

        db = sqlite(this, "tramiautos", null, 1)


        //Lista de colores
        colores = ArrayList()

        colores.add("AMARILLO")
        colores.add("AZULBEIGE")
        colores.add("BLANCO")
        colores.add("CAFÉ")
        colores.add("GRIS")
        colores.add("MARRON")
        colores.add("MORADO")
        colores.add("NARANJA")
        colores.add("NEGRO")
        colores.add("ROJO")
        colores.add("VERDE")

        //Lista de ciudades
        ciudades = ArrayList()

        ciudades.add("CARTAGO")
        ciudades.add("CALI")
        ciudades.add("BOGOTÁ")
        ciudades.add("MEDELLÍN")
        ciudades.add("BARRANQUILLA")

        //Lista de marcas autos
        marcas = ArrayList()

        marcas.add("AUDI")
        marcas.add("FIAT")
        marcas.add("ALFA ROMEO")

        guardarColores()
        guardarMarcas()
        guardarCiudades()

    }

    private fun guardarColores(){
        val con = db.writableDatabase

        for (i in colores){
            val values = ContentValues().apply {
                put("Color", i)
            }
            con.insert("tbl_colores", null, values)

        }
        Toast.makeText(this ,"guardados los colores", Toast.LENGTH_SHORT).show()
        con.close()
    }

    private fun guardarMarcas(){
        val con = db.writableDatabase

        for (i in marcas){
            val values = ContentValues().apply {
                put("Marca", i)
            }
            con.insert("tbl_marcautos", null, values)
        }
        Toast.makeText(this ,"guardados los marcas", Toast.LENGTH_SHORT).show()
        con.close()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int,  data: Intent?) {
        // Process the authorization result to obtain an ID token from AuthHuaweiId.
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 8888) {
            val authHuaweiIdTask = HuaweiIdAuthManager.parseAuthResultFromIntent(data)
            if (authHuaweiIdTask.isSuccessful) {
                // The sign-in is successful, and the user's HUAWEI ID information and ID token are obtained.
                val huaweiAccount = authHuaweiIdTask.result
                val mToken = huaweiAccount.idToken
                getToken()
                Toast.makeText(this ,"Inicio de sesión: $push", Toast.LENGTH_LONG).show()
                Log.i(TAG, "idToken: $mToken")
            } else {
                // The sign-in failed. No processing is required. Logs are recorded to facilitate fault locating.
                Toast.makeText(this ,"Falló al iniciar sesión", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "sign in failed : " + (authHuaweiIdTask.exception as ApiException).statusCode)
            }
        }
    }

    private fun getToken() {
        val tokenThread = Thread {
            var appId = ""
            try {
                appId = AGConnectServicesConfig.fromContext(applicationContext).getString("client/app_id")
                val pushtoken = HmsInstanceId.getInstance(applicationContext).getToken(appId, "HCM")
                if (!TextUtils.isEmpty(pushtoken)) {
                    Log.i("GetToken", "push token:$pushtoken")
                    push = appId
                }
            } catch (e: Exception) {
                Log.e("Token Exception", "getToken failed, $e")
                push = appId
//                push = "Token Exception"
            }
        }
        tokenThread.run()
    }


    private fun guardarCiudades(){
        val con = db.writableDatabase

        for (i in ciudades){
            val values = ContentValues().apply {
                put("Ciudad", i)
            }
            con.insert("tbl_ciudades", null, values)
        }
        Toast.makeText(this ,"guardados los ciudades", Toast.LENGTH_SHORT).show()
        con.close()
    }

}