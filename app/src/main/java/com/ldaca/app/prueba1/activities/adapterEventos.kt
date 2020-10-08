package com.ldaca.app.prueba1.activities

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ldaca.app.prueba1.R
import com.ldaca.app.prueba1.databinding.FragmentInicioBinding

class adapterEventos (var list: ArrayList<Eventos>) : RecyclerView.Adapter<adapterEventos.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.content_events, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.findEvents(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        fun findEvents(eventos : Eventos){

            var marca = itemView.findViewById<TextView>(R.id.marca)
            var placa = itemView.findViewById<TextView>(R.id.placa)

            var marca2 = itemView.findViewById<TextView>(R.id.marca2)
            var placa2 = itemView.findViewById<TextView>(R.id.placa2)

            var marca3 = itemView.findViewById<TextView>(R.id.marca3)
            var placa3 = itemView.findViewById<TextView>(R.id.placa3)


            var soap = itemView.findViewById<TextView>(R.id.soap)
            var tecno_mecanica = itemView.findViewById<TextView>(R.id.tecno_mecanica)
            var impuesto = itemView.findViewById<TextView>(R.id.impuesto)

            marca.text = eventos.marca
            placa.text = eventos.placa

            marca2.text = eventos.marca
            placa2.text = eventos.placa

            marca3.text = eventos.marca
            placa3.text = eventos.placa

            soap.text = eventos.soap
            tecno_mecanica.text = eventos.tecno_mecanica
            impuesto.text = eventos.impuesto

        }
    }

}