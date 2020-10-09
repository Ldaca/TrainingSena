package com.ldaca.app.prueba1.fragments

import android.app.Activity
import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Context
import android.content.pm.ActivityInfo
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.os.strictmode.SqliteObjectLeakedViolation
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import com.ldaca.app.prueba1.R
import com.ldaca.app.prueba1.activities.sqlite
import com.ldaca.app.prueba1.databinding.FragmentPerfilBinding
import com.ldaca.app.prueba1.databinding.FragmentRegistroBinding
import com.ldaca.app.prueba1.models.DateString
import kotlin.properties.Delegates

class PerfilFragment : Fragment() {

    private lateinit var binding: FragmentPerfilBinding
    private var dateNacimientoLicencia = DateString()
    private var seleccionFecha:Int? = 0
    private lateinit var idPerfil:String
    private var ExistePerfil:Int? = 0

    val db:sqlite? = sqlite(activity, "tramiautos", null, 1)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentPerfilBinding.inflate(layoutInflater, container, false)


        //Consultamos los datos que el usuario ha registrado
        //consultarDatos()

        //Guardamos los datos que son ingresados por el usuario, si existe un perfil se actualizan los datos
        binding.guardar.setOnClickListener {
            guardarDatos()
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

    private fun guardarDatos(){
        var con:SQLiteDatabase? = db!!.writableDatabase

        if(binding.nombres.text.isNotEmpty() && binding.nacimiento.text.isNotEmpty() && binding.email.text.isNotEmpty() && binding.licenciaFecha.text.isNotEmpty()){

            val values = ContentValues().apply {
                put("Nombres", binding.nombres.text.toString())
                put("FechaNacimiento", binding.nacimiento.text.toString())
                put("Email", binding.email.text.toString())
                put("FechaLicencia", binding.licenciaFecha.text.toString())
            }

            var insertar = con!!.insert("perfil", null, values)

            if(insertar>0){
                Toast.makeText(activity, "NUEVO REGISTRO GUARDADO.", Toast.LENGTH_LONG).show()
            }

            con.close()
        }else{
            Toast.makeText(activity,"Todos los campos son obligatorios",Toast.LENGTH_SHORT).show()
        }
    }

    private fun consultarDatos(){
        var con: SQLiteDatabase? = db!!.readableDatabase

        var cursor = con?.rawQuery("SELECT * FROM perfil", null)

        ExistePerfil = cursor!!.count

        Toast.makeText(activity, "Total = $ExistePerfil", Toast.LENGTH_LONG).show()

        if(ExistePerfil!!>0){
            while(cursor.moveToNext()){
                binding.nombres.setText(cursor.getString(1))
                binding.nacimiento.setText(cursor.getString(2))
                binding.email.setText(cursor.getString(3))
                binding.licenciaFecha.setText(cursor.getString(4))
                idPerfil = cursor.getString(0)
            }
            con?.close()
        }
    }

    private fun actualizarDatos(){
        var con:SQLiteDatabase? = db!!.writableDatabase

        if(binding.nombres.text.isNotEmpty() && binding.nacimiento.text.isNotEmpty() && binding.email.text.isNotEmpty() && binding.licenciaFecha.text.isNotEmpty()){
            val values = ContentValues().apply {
                put("Nombres", binding.nombres.text.toString())
                put("FechaNacimiento", binding.nacimiento.text.toString())
                put("Email", binding.email.text.toString())
                put("FechaLicencia", binding.licenciaFecha.text.toString())
            }
            var actualizar = con!!.update("perfil", values, "Id_perfil = $idPerfil", null)

            if(actualizar>0){
                Toast.makeText(activity, "REGISTRO ACTUALIZADO.", Toast.LENGTH_LONG).show()
            }
            con.close()
        }else{
            Toast.makeText(activity,"Todos los campos son obligatorios",Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PerfilFragment.
         */
        // TODO: Rename and change types and number of parameters
       /* @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PerfilFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }*/
    }

    override fun onDetach() {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        super.onDetach()
    }
}