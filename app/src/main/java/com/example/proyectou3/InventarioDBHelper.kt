package com.example.proyectou3

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class InventarioDBHelper(context: Context) :
    SQLiteOpenHelper(context, "CompuStock.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {

        val crearTabla = """
            CREATE TABLE equipos (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                tipo TEXT NOT NULL,
                marca TEXT NOT NULL,
                serie TEXT NOT NULL,
                estado TEXT NOT NULL
            )
        """.trimIndent()

        db.execSQL(crearTabla)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL("DROP TABLE IF EXISTS equipos")
        onCreate(db)
    }

    fun guardarEquipo(
        nombre: String,
        tipo: String,
        marca: String,
        serie: String,
        estado: String
    ): Long {

        val db = writableDatabase

        val valores = ContentValues().apply {
            put("nombre", nombre)
            put("tipo", tipo)
            put("marca", marca)
            put("serie", serie)
            put("estado", estado)
        }

        return db.insert("equipos", null, valores)
    }

    fun obtenerEquipos(): ArrayList<Equipo> {

        val lista = ArrayList<Equipo>()
        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM equipos ORDER BY id DESC",
            null
        )

        cursor.use {
            while (it.moveToNext()) {

                val equipo = Equipo(
                    id = it.getInt(0),
                    nombre = it.getString(1),
                    tipo = it.getString(2),
                    marca = it.getString(3),
                    serie = it.getString(4),
                    estado = it.getString(5)
                )

                lista.add(equipo)
            }
        }

        return lista
    }

    fun actualizarEquipo(
        id: Int,
        nombre: String,
        tipo: String,
        marca: String,
        serie: String
    ): Int {
        val db = writableDatabase

        val valores = ContentValues().apply {
            put("nombre", nombre)
            put("tipo", tipo)
            put("marca", marca)
            put("serie", serie)
        }

        return db.update(
            "equipos",
            valores,
            "id = ?",
            arrayOf(id.toString())
        )
    }

}