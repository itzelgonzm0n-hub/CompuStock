package com.example.proyectou3

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectou3.databinding.ActivityEditarEquipoBinding

class EditarEquipoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditarEquipoBinding
    private lateinit var dbHelper: InventarioDBHelper
    private var equipos = ArrayList<Equipo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditarEquipoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = InventarioDBHelper(this)

        configurarTipos()
        cargarEquipos()

        binding.btnGuardarEdicion.setOnClickListener {
            guardarCambios()
        }

        binding.btnVolver.setOnClickListener {
            finish()
        }
    }

    private fun configurarTipos() {
        val tipos = arrayOf(
            "Computadora",
            "Monitor",
            "Teclado",
            "Ratón"
        )

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            tipos
        )

        adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        binding.spTipoEditar.adapter = adapter
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

        binding.spEquipos.adapter = adapter

        if (equipos.isNotEmpty()) {
            mostrarDatos(0)
        }

        binding.spEquipos.onItemSelectedListener =
            object : android.widget.AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: android.widget.AdapterView<*>?,
                    view: android.view.View?,
                    position: Int,
                    id: Long
                ) {
                    mostrarDatos(position)
                }

                override fun onNothingSelected(
                    parent: android.widget.AdapterView<*>?
                ) {}
            }
    }

    private fun mostrarDatos(position: Int) {
        if (position !in equipos.indices) return

        val equipo = equipos[position]

        binding.etNombreEditar.setText(equipo.nombre)
        binding.etMarcaEditar.setText(equipo.marca)
        binding.etSerieEditar.setText(equipo.serie)

        val tipos = resources.getStringArray(R.array.tipos_equipo)
        val posicionTipo = tipos.indexOf(equipo.tipo)

        if (posicionTipo >= 0) {
            binding.spTipoEditar.setSelection(posicionTipo)
        }
    }

    private fun guardarCambios() {
        if (equipos.isEmpty()) {
            Toast.makeText(
                this,
                "No hay equipos para editar",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val equipo = equipos[binding.spEquipos.selectedItemPosition]

        val nombre = binding.etNombreEditar.text.toString().trim()
        val tipo = binding.spTipoEditar.selectedItem.toString()
        val marca = binding.etMarcaEditar.text.toString().trim()
        val serie = binding.etSerieEditar.text.toString().trim()

        if (nombre.isEmpty() || marca.isEmpty() || serie.isEmpty()) {
            Toast.makeText(
                this,
                "Completa todos los campos",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val resultado = dbHelper.actualizarEquipo(
            equipo.id,
            nombre,
            tipo,
            marca,
            serie
        )

        if (resultado > 0) {
            Toast.makeText(
                this,
                "Equipo actualizado correctamente",
                Toast.LENGTH_SHORT
            ).show()

            cargarEquipos()
        } else {
            Toast.makeText(
                this,
                "No se pudieron guardar los cambios",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }
}