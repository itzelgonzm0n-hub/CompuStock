package com.example.proyectou3

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectou3.databinding.ActivityActualizarEstadoBinding

class ActualizarEstadoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityActualizarEstadoBinding
    private lateinit var dbHelper: InventarioDBHelper
    private var equipos = ArrayList<Equipo>()

    private val estados = arrayOf(
        "Funciona",
        "No funciona"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityActualizarEstadoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = InventarioDBHelper(this)

        configurarEstados()
        cargarEquipos()

        binding.btnGuardarEstado.setOnClickListener {
            guardarEstado()
        }

        binding.btnVolver.setOnClickListener {
            finish()
        }
    }

    private fun configurarEstados() {
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            estados
        )

        adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        binding.spNuevoEstado.adapter = adapter
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

        binding.spEstadoEquipo.adapter = adapter

        binding.btnGuardarEstado.isEnabled = equipos.isNotEmpty()

        if (equipos.isEmpty()) {
            Toast.makeText(
                this,
                "No hay equipos registrados",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.spEstadoEquipo.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position in equipos.indices) {
                        val estadoActual = equipos[position].estado
                        val posicionEstado = estados.indexOf(estadoActual)

                        if (posicionEstado >= 0) {
                            binding.spNuevoEstado.setSelection(posicionEstado)
                        }
                    }
                }

                override fun onNothingSelected(
                    parent: AdapterView<*>?
                ) {}
            }
    }

    private fun guardarEstado() {
        if (equipos.isEmpty()) return

        val posicion = binding.spEstadoEquipo.selectedItemPosition

        if (posicion !in equipos.indices) return

        val equipo = equipos[posicion]
        val nuevoEstado = binding.spNuevoEstado.selectedItem.toString()

        val resultado = dbHelper.actualizarEstado(
            equipo.id,
            nuevoEstado
        )

        if (resultado > 0) {
            Toast.makeText(
                this,
                "Estado actualizado correctamente",
                Toast.LENGTH_SHORT
            ).show()

            cargarEquipos()
        } else {
            Toast.makeText(
                this,
                "No se pudo actualizar el estado",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }
}