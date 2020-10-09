package com.ldaca.app.prueba1.activities

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class sqlite(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(tramiautos: SQLiteDatabase?) {
        tramiautos!!.execSQL("CREATE TABLE perfil( Id_perfil integer primary key autoincrement, Nombres text, FechaNacimiento text, Email text, FechaLicencia text )")
        tramiautos.execSQL("CREATE TABLE tbl_impuesto_carro( Id integer primary key autoincrement, Ciudad text, Mes text, Numero_ini text, Numero_fin text, Año text )")
        tramiautos.execSQL("CREATE TABLE tbl_ciudades( Id integer primary key autoincrement, Ciudad text)")
        tramiautos.execSQL("CREATE TABLE tbl_colores( Id integer primary key autoincrement, Color text)")
        tramiautos.execSQL("CREATE TABLE tbl_marcautos( Id integer primary key autoincrement, Marca text )")
        tramiautos.execSQL("CREATE TABLE tbl_regautos( Id integer primary key autoincrement, Marca text, Color text,Placa text, Ciudad text, Modelo text, FechaSoat text )")

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("Drop table if exists perfil")
        db.execSQL("Drop table if exists tbl_impuesto_carro")
        db.execSQL("Drop table if exists tbl_ciudades")
        db.execSQL("Drop table if exists tbl_colores")
        db.execSQL("Drop table if exists tbl_marcautos")
        db.execSQL("Drop table if exists tbl_regautos")
        onCreate(db)
    }
}