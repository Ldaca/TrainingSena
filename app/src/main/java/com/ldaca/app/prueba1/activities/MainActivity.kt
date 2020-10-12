package com.ldaca.app.prueba1.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.FileProvider
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.ldaca.app.prueba1.R
import com.ldaca.app.prueba1.databinding.ActivityMainBinding
import com.ldaca.app.prueba1.fragments.*
import java.io.File
import java.io.IOException

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var toolbar: Toolbar
    private lateinit var actionBar: ActionBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar()
        initDrawer();
    }

    private fun initDrawer() {
        val menu = binding.navView.menu
        val oldItem = menu.getItem(0)
        binding.navView.setNavigationItemSelectedListener(this)
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawer,
            toolbar,
            R.string.openDrawer,
            R.string.closeDrawer
        )
        binding.drawer.addDrawerListener(toggle)
        toggle.syncState()
        selectedItem(oldItem)
        oldItem.isChecked = true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        selectedItem(item)
        binding.drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun selectedItem(item: MenuItem) {
        var fragmentTransaction = false
        var fragment: Fragment? = null
        when (item.itemId) {
            R.id.inicio -> {
                fragmentTransaction = true
                fragment = InicioFragment()
                actionBar.title = "TramiAutos"
            }
            R.id.registro -> {
                fragmentTransaction = true
                fragment = RegistroFragment()
                actionBar.title = item.title
            }
            R.id.servicios -> {
                fragmentTransaction = true
                fragment = ServiciosFragment()
                actionBar.title = item.title
            }
            R.id.perfil -> {
                fragmentTransaction = true
                fragment = PerfilFragment()
                actionBar.title = item.title
            }
            R.id.acerca -> {
                fragmentTransaction = true
                fragment = AcercaDeFragment()
                actionBar.title = item.title
            }
        }
        if (fragmentTransaction) {
            val transaction = supportFragmentManager.beginTransaction().replace(R.id.content_frame, fragment!!)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    private fun setToolbar() {
        toolbar = binding.content.appbar.toolbar
        setSupportActionBar(toolbar)
        actionBar = supportActionBar!!
        actionBar.setDisplayHomeAsUpEnabled(false)
        actionBar.title = getString(R.string.app_name)
    }
}