package com.ldaca.app.prueba1.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ldaca.app.prueba1.databinding.FragmentServiciosBinding


/**
 * A simple [Fragment] subclass.
 */
class ServiciosFragment : Fragment() {
    private lateinit var binding: FragmentServiciosBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentServiciosBinding.inflate(layoutInflater, container, false)

        binding.soat.setOnCheckedChangeListener { _, state ->
            if (state) {
                Toast.makeText(context, "SOAT", Toast.LENGTH_SHORT).show()
            }
        }

        binding.revision.setOnCheckedChangeListener { _, state ->
            if (state) {
                Toast.makeText(context, "REVISIÓN", Toast.LENGTH_SHORT).show()
            }
        }

        binding.pago.setOnCheckedChangeListener { _, state ->
            if (state) {
                Toast.makeText(context, "PAGO", Toast.LENGTH_SHORT).show()
            }
        }

        binding.pico.setOnCheckedChangeListener { _, state ->
            if (state) {
                Toast.makeText(context, "PICO Y PLACA", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

}