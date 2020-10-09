package com.ldaca.app.prueba1.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ldaca.app.prueba1.R
import com.ldaca.app.prueba1.activities.Eventos
import com.ldaca.app.prueba1.activities.adapterEventos
import com.ldaca.app.prueba1.databinding.FragmentInicioBinding
import java.util.prefs.Preferences


/**
 * A simple [Fragment] subclass.
 * Use the [InicioFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InicioFragment : Fragment() {

    private lateinit var binding:FragmentInicioBinding
    private lateinit var recyclerView_eventos: RecyclerView
    private lateinit var list_eventos:ArrayList<Eventos>



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentInicioBinding.inflate(layoutInflater, container, false)

        recyclerView_eventos = binding.recyclerEvents
        recyclerView_eventos.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        list_eventos = ArrayList()

        list_eventos.add(Eventos("AUDI", "FGL-548", "20/12/2020", "05/10/2020", "01/01/2021"))

        list_eventos.add(Eventos("BMW", "AAQ-210", "20/12/2020", "05/10/2020", "01/01/2021"))

        list_eventos.add(Eventos("Ferrari", "WXL-708", "20/12/2020", "05/10/2020", "01/01/2021"))

        val adapter = adapterEventos(list_eventos)

        recyclerView_eventos.adapter = adapter

        return binding.root

    }

    companion object {

    }
}