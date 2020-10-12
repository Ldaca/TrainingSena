package com.ldaca.app.prueba1.fragments

import android.annotation.SuppressLint
import android.content.Intent
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
import com.ldaca.app.prueba1.services.LicenciaService


/**
 * A simple [Fragment] subclass.
 * Use the [InicioFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InicioFragment : Fragment() {

    private lateinit var binding:FragmentInicioBinding
    private lateinit var recyclerView_eventos: RecyclerView
    private lateinit var list_eventos:ArrayList<Eventos>

    private lateinit var preferences : SharedPreferences

    private lateinit var db:sqlite

    override fun onStart() {
        super.onStart()
//        serviceStart()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentInicioBinding.inflate(layoutInflater, container, false)

        //Iniciamos la base de datos
        db = sqlite(activity, "tramiautos", null, 1)

        // Se inicializa las SharedPreference
        preferences = activity?.getSharedPreferences("Preferencias", Context.MODE_PRIVATE)!!

        recyclerView_eventos = binding.recyclerEvents
        recyclerView_eventos.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        list_eventos = ArrayList()

        consultarDatos()

        val adapter = adapterEventos(list_eventos)

        recyclerView_eventos.adapter = adapter

        if(isThereProfile()) {
            MostrarMensaje()
        }

        return binding.root

    }

    @SuppressLint("Recycle")
    private fun consultarDatos(){
        val con = db.readableDatabase

        val cursor = con.rawQuery("SELECT * FROM tbl_regautos ORDER BY FechaSoat ASC", null)

        while(cursor.moveToNext()){
            list_eventos.add(Eventos(cursor.getString(1), cursor.getString(3), cursor.getString(6), "00/00/0000", "00/00/0000"))
        }
        con.close()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("Recycle", "SimpleDateFormat")
    private fun MostrarMensaje(){
        var nombreUsuario = preferences.getString("nombre", "")
        var fechaNacimiento = preferences.getString("dateBorn", "00/00/0000")

        var cantidad_años = 0
        var cantidad_meses = 0
        var cantidad_dias = 0

        //convertir la fecha de nacimiento a tipo date
        var parsedDate = LocalDate.parse(fechaNacimiento, DateTimeFormatter.ofPattern("dd/MM/yyyy"))

        //Año actual
        val formatoaño = SimpleDateFormat("YYYY")
        val AñoActual:Int = formatoaño.format(Date()).toInt()

        //Obtenemos solo el año de la fecha de nacimiento
        val añoNacimiento:Int = parsedDate.year

        //operamos para sacar la edad del usuario
        var edad:Int = AñoActual - añoNacimiento

        when {
            edad<60 -> {
                cantidad_años = 10
                cantidad_meses = cantidad_años * 12
                cantidad_dias = cantidad_años * 365
            }
            edad in 60..80 -> {
                cantidad_años = 5
                cantidad_meses = cantidad_años * 12
                cantidad_dias = cantidad_años * 365
            }
            edad>80 -> {
                cantidad_años = 1
                cantidad_meses = cantidad_años * 12
                cantidad_dias = cantidad_años * 365
            }
        }

        Toast.makeText(activity, "Estimado@ $nombreUsuario, faltan $cantidad_años años, $cantidad_meses meses y $cantidad_dias dias para el vencimiento de su pase. \n \n Recuerde renovarlo y evitar contratiempos. ", Toast.LENGTH_LONG).show()

    }

    private fun isThereProfile() = preferences.getBoolean("exist", false)

    companion object {

    }

    private fun serviceStart() {
        val intent = Intent(context, LicenciaService::class.java)
        activity?.startService(intent)
    }

    private fun serviceStopt() {val intent = Intent(context, LicenciaService::class.java)
        activity?.stopService(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
//        serviceStopt()
    }
}