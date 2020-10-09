package com.ldaca.app.prueba1.fragments

import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ldaca.app.prueba1.R
import com.ldaca.app.prueba1.activities.sqlite
import com.ldaca.app.prueba1.databinding.FragmentRegistroBinding
import com.ldaca.app.prueba1.models.DateString
import java.util.ArrayList

/**
 * A simple [Fragment] subclass..
 */
class RegistroFragment : Fragment() {
    private lateinit var binding: FragmentRegistroBinding
    private var dateSoat = DateString()

    private lateinit var db: sqlite
    private lateinit var placa_format:String


    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRegistroBinding.inflate(layoutInflater, container, false)

        // Configuracion del Spinner de marca
        val adapterMarca = ArrayAdapter(requireActivity(),R.layout.item_spinner, getMarcas())
        adapterMarca.setDropDownViewResource(R.layout.spinner_dropdown_item)
        binding.marca.adapter = adapterMarca

        // Configuracion del Spinner de color
        val adapterColor = ArrayAdapter(requireActivity(),R.layout.item_spinner, getColores())
        adapterColor.setDropDownViewResource(R.layout.spinner_dropdown_item)
        binding.color.adapter = adapterColor

        // Configuracion del Spinner de ciudad
        val adapterCiudad = ArrayAdapter(requireActivity(),R.layout.item_spinner, getCiudades())
        adapterCiudad.setDropDownViewResource(R.layout.spinner_dropdown_item)
        binding.ciudad.adapter = adapterCiudad

        //Configuracion del listener en foco del EditText
        binding.fecha.setOnFocusChangeListener { _, isFocus ->
            if (isFocus) getDate()
        }

        //Configuracion del listener en el logo del calendario
        binding.ibGetDate.setOnClickListener {
            getDate()
        }

        //Inicializamos la base de datos
        db = sqlite(activity, "tramiautos", null, 1)

        //Guadar registro de vehiculos
        binding.save.setOnClickListener {
            guardarVehiculos()
        }

        return binding.root
    }

    private fun guardarVehiculos(){
        var con =  db.writableDatabase

        if(binding.placa.text.isNotEmpty() && binding.modelo.text.isNotEmpty() && binding.fecha.text.isNotEmpty()){
            var values = ContentValues().apply {
                put("Marca", binding.marca.selectedItem.toString())
                put("Color", binding.color.selectedItem.toString())
                put("Placa", binding.placa.text.toString())
                put("Ciudad",binding.ciudad.selectedItem.toString())
                put("Modelo", binding.modelo.text.toString())
                put("FechaSoat", binding.fecha.text.toString())
            }

            var insertar = con.insert("tbl_regautos", null, values)

            if(insertar>0){
                Toast.makeText(activity, "REGISTRO EXITOSO", Toast.LENGTH_SHORT).show()
            }
            con.close()

        }else{
            Toast.makeText(activity, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getMarcas(isDB: Boolean = false): List<String> {
        val list = if (isDB) {
            getMarcasFromDB()
        } else {
            resources.getStringArray(R.array.marcas).toList()
        }
        return list
    }

    private fun getColores(isDB: Boolean = false): List<String> {
        val list = if (isDB) {
            getColoresFromDB()
        } else {
            resources.getStringArray(R.array.colores).toList()
        }
        return list
    }

    private fun getCiudades(isDB: Boolean = false): List<String> {
        val list = if (isDB) {
            getCiudadesFromDB()
        } else {
            resources.getStringArray(R.array.ciudades).toList()
        }
        return list
    }

    private fun getMarcasFromDB(): List<String>{

        var list_marcas = ArrayList<String>()

        var con = db.readableDatabase

        var cursor = con.rawQuery("SELECT * FROM tbl_marcautos", null)

        while (cursor.moveToNext()){
            list_marcas.add(cursor.getString(1))
        }

        return list_marcas
    }

    private fun getColoresFromDB(): List<String> {

        var list_colores = ArrayList<String>()

        var con = db.readableDatabase

        var cursor = con.rawQuery("SELECT * FROM tbl_colores", null)

        while (cursor.moveToNext()){
            list_colores.add(cursor.getString(1))
        }

        return list_colores
    }

    private fun getCiudadesFromDB(): List<String> {

        var list_ciudades = ArrayList<String>()

        var con = db.readableDatabase

        var cursor = con.rawQuery("SELECT * FROM tbl_ciudades", null)

        while (cursor.moveToNext()){
            list_ciudades.add(cursor.getString(1))
        }

        return list_ciudades
    }

    private fun getDate() {
        val showDate = DatePickerDialog(context!!, R.style.AppDialog, { _: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->
                dateSoat.setDate(dayOfMonth, month + 1, year)
                binding.fecha.setText(dateSoat.displayDate)
            },
            dateSoat.year.toInt(),
            dateSoat.month.toInt(),
            dateSoat.day.toInt()
        )
        showDate.show()
    }

    override fun onDetach() {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        super.onDetach()
    }
}