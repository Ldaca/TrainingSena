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
import com.ldaca.app.prueba1.activities.sqlite
import com.ldaca.app.prueba1.databinding.FragmentInicioBinding
import java.util.prefs.Preferences
import kotlin.math.E


/**
 * A simple [Fragment] subclass.
 * Use the [InicioFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InicioFragment : Fragment() {

    private lateinit var binding:FragmentInicioBinding
    private lateinit var recyclerView_eventos: RecyclerView
    private lateinit var list_eventos:ArrayList<Eventos>

    private lateinit var db:sqlite

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentInicioBinding.inflate(layoutInflater, container, false)

        //Iniciamos la base de datos
        db = sqlite(activity, "tramiautos", null, 1)

        recyclerView_eventos = binding.recyclerEvents
        recyclerView_eventos.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        list_eventos = ArrayList()

        consultarDatos()

        val adapter = adapterEventos(list_eventos)

        recyclerView_eventos.adapter = adapter

        return binding.root

    }

    private fun consultarDatos(){
        var con = db.readableDatabase

        var cursor = con.rawQuery("SELECT * FROM tbl_regautos ORDER BY Id DESC", null)

        while(cursor.moveToNext()){
            list_eventos.add(Eventos(cursor.getString(1), cursor.getString(3), cursor.getString(6), "00/00/0000", "00/00/0000"))
        }
        con.close()
    }

    companion object {

    }
}