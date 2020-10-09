package com.ldaca.app.prueba1.activities

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.huawei.hms.support.hwid.HuaweiIdAuthManager
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParams
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParamsHelper
import com.huawei.hms.support.hwid.service.HuaweiIdAuthService
import com.ldaca.app.prueba1.databinding.ActivityMainPreBinding

class PreMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainPreBinding
    private val authParams : HuaweiIdAuthParams by lazy { HuaweiIdAuthParamsHelper(HuaweiIdAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
        .setIdToken().createParams() }
    private val service : HuaweiIdAuthService by lazy { HuaweiIdAuthManager.getService(this@PreMainActivity, authParams) }

    private lateinit var db:sqlite
    lateinit var colores: ArrayList<String>
    lateinit var ciudades: ArrayList<String>
    lateinit var marcas: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainPreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.signUp.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.sign1.setOnClickListener {
            startActivityForResult(service.signInIntent, 8888)
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
        var con = db.writableDatabase

        var values = ContentValues().apply {
            for (i in colores){
                put("Color", i)
            }
        }
        Toast.makeText(this ,"guardados los colores", Toast.LENGTH_SHORT).show()
        con.insert("tbl_colores", null, values)
        con.close()
    }

    private fun guardarMarcas(){
        var con = db.writableDatabase

        var values = ContentValues().apply {
            for (i in marcas){
                put("Marca", i)
            }
        }
        Toast.makeText(this ,"guardados los marcas", Toast.LENGTH_SHORT).show()
        con.insert("tbl_marcautos", null, values)
        con.close()
    }

    private fun guardarCiudades(){
        var con = db.writableDatabase

        var values = ContentValues().apply {
            for (i in ciudades){
                put("Ciudad", i)
            }
        }
        Toast.makeText(this ,"guardados los ciudades", Toast.LENGTH_SHORT).show()
        con.insert("tbl_ciudades", null, values)
        con.close()
    }
}