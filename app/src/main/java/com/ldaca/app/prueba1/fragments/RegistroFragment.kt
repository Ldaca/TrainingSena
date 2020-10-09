package com.ldaca.app.prueba1.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import com.ldaca.app.prueba1.R
import com.ldaca.app.prueba1.databinding.FragmentRegistroBinding
import com.ldaca.app.prueba1.models.DateString

/**
 * A simple [Fragment] subclass..
 */
class RegistroFragment : Fragment() {
    private lateinit var binding: FragmentRegistroBinding
    private var dateSoat = DateString()


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
        val adapterCiudad = ArrayAdapter(requireActivity(),R.layout.item_spinner, getColores())
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
        return binding.root
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
        return emptyList<String>().toMutableList()
    }

    private fun getColoresFromDB(): List<String> {
        return emptyList<String>().toMutableList()
    }

    private fun getCiudadesFromDB(): List<String> {
        return emptyList<String>().toMutableList()
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