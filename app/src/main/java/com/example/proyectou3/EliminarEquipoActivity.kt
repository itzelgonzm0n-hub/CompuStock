package com.example.proyectou3

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectou3.databinding.ActivityEliminarEquipoBinding

class EliminarEquipoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEliminarEquipoBinding
    private lateinit var dbHelper: InventarioDBHelper
    private var equipos = ArrayList<Equipo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEliminarEquipoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = InventarioDBHelper(this)

        cargarEquipos()

        binding.btnConfirmarEliminar.setOnClickListener {
            confirmarEliminacion()
        }

        binding.btnVolver.setOnClickListener {
            finish()
        }
    }

    private fun cargarEquipos() {
        equipos = dbHelper.obtenerEquipos()

        val nombres = equipos.map {
            "${it.nombre} - ${it.serie}"
        }

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            nombres
        )

        adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        binding.spEliminarEquipo.adapter = adapter

        binding.btnConfirmarEliminar.isEnabled = equipos.isNotEmpty()

        if (equipos.isEmpty()) {
            Toast.makeText(
                this,
                "No hay equipos registrados",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun confirmarEliminacion() {
        if (equipos.isEmpty()) return

        val posicion = binding.spEliminarEquipo.selectedItemPosition
        val equipo = equipos[posicion]

        AlertDialog.Builder(this)
            .setTitle("Confirmar eliminación")
            .setMessage(
                "¿Estás seguro de eliminar ${equipo.nombre}?"
            )
            .setPositiveButton("Eliminar") { _, _ ->
                val resultado = dbHelper.eliminarEquipo(equipo.id)

                if (resultado > 0) {
                    Toast.makeText(
                        this,
                        "Equipo eliminado correctamente",
                        Toast.LENGTH_SHORT
                    ).show()

                    cargarEquipos()
                } else {
                    Toast.makeText(
                        this,
                        "No se pudo eliminar el equipo",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }
}