package com.ldaca.app.prueba1.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ldaca.app.prueba1.R
import com.ldaca.app.prueba1.activities.sqlite
import com.ldaca.app.prueba1.databinding.FragmentPerfilBinding
import com.ldaca.app.prueba1.models.DateString

class PerfilFragment : Fragment() {

    private lateinit var binding: FragmentPerfilBinding
    private lateinit var preferences : SharedPreferences
    private var dateNacimientoLicencia = DateString()
    private var seleccionFecha:Int? = 0
    private lateinit var idPerfil:String
    private var ExistePerfil:Int? = 0

    private lateinit var db: sqlite

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentPerfilBinding.inflate(layoutInflater, container, false)

        // Se inicializa las SharedPreference
        preferences = activity?.getSharedPreferences("Preferencias", Context.MODE_PRIVATE)!!

        //Consultamos los datos que el usuario ha registrado
        db = sqlite(activity, "tramiautos", null, 1)
        consultarDatos(false)

        //Guardamos los datos que son ingresados por el usuario, si existe un perfil se actualizan los datos
        binding.guardar.setOnClickListener {
            if (ExistePerfil == 0) {
                guardarDatos(false)
            } else {
                actualizarDatos(false)
            }
        }

        //Configuracion del listener en el editext de nacimiento
        binding.nacimiento.setOnClickListener {
            seleccionFecha = 1
            getDate()
        }

        //Configuracion del listener en el editext de licencia
        binding.licenciaFecha.setOnClickListener {
            seleccionFecha = 2
            getDate()
        }

        return binding.root
    }

    private fun getDate() {
        val showDate = DatePickerDialog(context!!, R.style.AppDialog, { _: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->
            dateNacimientoLicencia.setDate(dayOfMonth, month + 1, year)
            if(seleccionFecha==1){
                binding.nacimiento.setText(dateNacimientoLicencia.displayDate)
            }else{
                binding.licenciaFecha.setText(dateNacimientoLicencia.displayDate)
            }
        },
            dateNacimientoLicencia.year.toInt(),
            dateNacimientoLicencia.month.toInt(),
            dateNacimientoLicencia.day.toInt()
        )
        showDate.show()
    }

    private fun guardarDatos(isDB: Boolean = true){
        val con:SQLiteDatabase? = db.writableDatabase

        if (isDB) {
            if(binding.nombres.text.isNotEmpty() && binding.nacimiento.text.isNotEmpty() && binding.email.text.isNotEmpty() && binding.licenciaFecha.text.isNotEmpty()){

                val values = ContentValues().apply {
                    put("Nombres", binding.nombres.text.toString())
                    put("FechaNacimiento", binding.nacimiento.text.toString())
                    put("Email", binding.email.text.toString())
                    put("FechaLicencia", binding.licenciaFecha.text.toString())
                }

                val insertar = con!!.insert("perfil", null, values)

                if(insertar>0){
                    Toast.makeText(activity, "NUEVO REGISTRO GUARDADO.", Toast.LENGTH_LONG).show()
                }

                con.close()
            }else{
                Toast.makeText(activity,"Todos los campos son obligatorios",Toast.LENGTH_SHORT).show()
            }
        } else {
            saveOrUpdatePreference()
        }
    }

    @SuppressLint("Recycle")
    private fun consultarDatos(isDB: Boolean = true){
        val con: SQLiteDatabase? = db.readableDatabase

        if (isDB) {
            val cursor = con?.rawQuery("SELECT * FROM perfil", null)

            ExistePerfil = cursor!!.count

            if(ExistePerfil!!>0){
                while(cursor.moveToNext()){
                    binding.nombres.setText(cursor.getString(1))
                    binding.nacimiento.setText(cursor.getString(2))
                    binding.email.setText(cursor.getString(3))
                    binding.licenciaFecha.setText(cursor.getString(4))
                    idPerfil = cursor.getString(0)
                }
                con.close()
            }
        } else {
            getProfile()
        }
    }

    private fun actualizarDatos(isDB: Boolean = true){
        val con:SQLiteDatabase? = db.writableDatabase

        if (isDB) {
            if(binding.nombres.text.isNotEmpty() && binding.nacimiento.text.isNotEmpty() && binding.email.text.isNotEmpty() && binding.licenciaFecha.text.isNotEmpty()){
                val values = ContentValues().apply {
                    put("Nombres", binding.nombres.text.toString())
                    put("FechaNacimiento", binding.nacimiento.text.toString())
                    put("Email", binding.email.text.toString())
                    put("FechaLicencia", binding.licenciaFecha.text.toString())
                }
                val actualizar = con!!.update("perfil", values, "Id_perfil = $idPerfil", null)

                if(actualizar>0){
                    Toast.makeText(activity, "REGISTRO ACTUALIZADO.", Toast.LENGTH_LONG).show()
                }
                con.close()
            }else{
                Toast.makeText(activity,"Todos los campos son obligatorios",Toast.LENGTH_SHORT).show()
            }
        } else {
            saveOrUpdatePreference()
        }
    }

    @SuppressLint("CommitPrefEdits")
    private fun saveOrUpdatePreference() {
        val name = binding.nombres.text.toString()
        val dateBorn = binding.nacimiento.text.toString()
        val mail = binding.email.text.toString()
        val dateLicencia = binding.licenciaFecha.text.toString()

        preferences.edit().run {
            putString("nombre", name)
            putString("dateBorn", dateBorn)
            putString("mail", mail)
            putString("dateLicencia", dateLicencia)
            putBoolean("exist", true)
            apply()
        }
        Toast.makeText(activity, "REGISTRO ACTUALIZADO.", Toast.LENGTH_LONG).show()
    }

    private fun getProfile() {
        binding.nombres.setText(preferences.getString("nombre", ""))
        binding.nacimiento.setText(preferences.getString("dateBorn", ""))
        binding.email.setText(preferences.getString("mail", ""))
        binding.licenciaFecha.setText(preferences.getString("dateLicencia", ""))
    }

    override fun onDetach() {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        super.onDetach()
    }
}