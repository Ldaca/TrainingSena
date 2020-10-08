package com.ldaca.app.prueba1.fragments

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ldaca.app.prueba1.R
import com.ldaca.app.prueba1.databinding.FragmentRegistroBinding
import com.ldaca.app.prueba1.databinding.ToolbarHomeBinding

/**
 * A simple [Fragment] subclass..
 */
class RegistroFragment : Fragment() {
    private lateinit var binding: FragmentRegistroBinding


    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRegistroBinding.inflate(layoutInflater, container, false)

        // Configuracion del Spinner de marca
        val adapterMarca = ArrayAdapter.createFromResource(requireActivity(), R.array.marcas, R.layout.item_spinner)
        adapterMarca.setDropDownViewResource(R.layout.spinner_dropdown_item)
        binding.marca.adapter = adapterMarca

        // Configuracion del Spinner de color
        val adapterColor = ArrayAdapter.createFromResource(requireActivity(), R.array.colores, R.layout.item_spinner)
        adapterColor.setDropDownViewResource(R.layout.spinner_dropdown_item)
        binding.color.adapter = adapterColor

        // Configuracion del Spinner de ciudad
        val adapterCiudad = ArrayAdapter.createFromResource(requireActivity(), R.array.ciudades, R.layout.item_spinner)
        adapterCiudad.setDropDownViewResource(R.layout.spinner_dropdown_item)
        binding.ciudad.adapter = adapterCiudad





        return binding.root
    }

    companion object {

//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            RegistroFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
    }

    override fun onDetach() {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        super.onDetach()
    }
}