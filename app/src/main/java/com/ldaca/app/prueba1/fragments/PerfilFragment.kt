package com.ldaca.app.prueba1.fragments

import android.app.Activity
import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
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

    val db = sqlite(activity, "tramiautos", null, 1)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentPerfilBinding.inflate(layoutInflater, container, false)


        binding.guardar.setOnClickListener {
            Toast.makeText(activity, "Nombres y Apellidos = ${binding.nombres.text} \n Fecha Nacimiento = ${binding.nacimiento.text} \n " +
                    "Email = ${binding.email.text} \n Vencimiento Licencia = ${binding.licenciaFecha.text} ", Toast.LENGTH_LONG).show()
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
        val con = db.writableDatabase

        val values = ContentValues().apply {
            put("Nombres", "Laura Sanchez")
        }

        con.insert("perfil", null, values)

        con.close()

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