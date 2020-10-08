package com.ldaca.app.prueba1.fragments

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ldaca.app.prueba1.databinding.FragmentRegistroBinding

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