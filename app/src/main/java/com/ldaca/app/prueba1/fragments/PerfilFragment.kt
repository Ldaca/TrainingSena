package com.ldaca.app.prueba1.fragments

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ldaca.app.prueba1.R
import com.ldaca.app.prueba1.databinding.FragmentPerfilBinding
import com.ldaca.app.prueba1.databinding.FragmentRegistroBinding

class PerfilFragment : Fragment() {

    private lateinit var binding: FragmentPerfilBinding

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentPerfilBinding.inflate(layoutInflater, container, false)


        binding.guardar.setOnClickListener {
            Toast.makeText(activity, "Nombres y Apellidos = ${binding.nombres.text} \n Fecha Nacimiento = ${binding.nacimiento.text} \n " +
                    "Email = ${binding.email.text} \n Vencimiento Licencia = ${binding.licenciaFecha.text} ", Toast.LENGTH_LONG).show()
        }

        return binding.root
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