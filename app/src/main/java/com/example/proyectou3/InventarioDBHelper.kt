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
        fun obtenerEquipos(): ArrayList<Equipo> {

            val lista = ArrayList<Equipo>()
            val db = readableDatabase

            val cursor = db.rawQuery(
                "SELECT * FROM equipos ORDER BY id DESC",
                null
            )

            if (cursor.moveToFirst()) {
                do {
                    val equipo = Equipo(
                        id = cursor.getInt(0),
                        nombre = cursor.getString(1),
                        tipo = cursor.getString(2),
                        marca = cursor.getString(3),
                        serie = cursor.getString(4),
                        estado = cursor.getString(5)
                    )

                    lista.add(equipo)

                } while (cursor.moveToNext())
            }

            cursor.close()

            return lista
        }


        return db.insert("equipos", null, valores)
    }
}