package com.example.proyectou3

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectou3.databinding.ActivityRegistrarBinding

class RegistrarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrarBinding
    private lateinit var dbHelper: InventarioDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistrarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = InventarioDBHelper(this)

        configurarListas()

        binding.btnGuardar.setOnClickListener {
            guardarEquipo()
        }

        binding.btnVolver.setOnClickListener {
            finish()
        }
    }

    private fun configurarListas() {

        val tipos = listOf(
            "Computadora",
            "Monitor",
            "Teclado",
            "Ratón"
        )

        val estados = listOf(
            "Funciona",
            "No funciona"
        )

        val adaptadorTipos = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            tipos
        )

        adaptadorTipos.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        binding.spTipo.adapter = adaptadorTipos

        val adaptadorEstados = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            estados
        )

        adaptadorEstados.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        binding.spEstado.adapter = adaptadorEstados
    }

    private fun guardarEquipo() {

        val nombre = binding.etNombre.text.toString().trim()
        val tipo = binding.spTipo.selectedItem.toString()
        val marca = binding.etMarca.text.toString().trim()
        val serie = binding.etSerie.text.toString().trim()
        val estado = binding.spEstado.selectedItem.toString()

        if (nombre.isEmpty() || marca.isEmpty() || serie.isEmpty()) {
            Toast.makeText(
                this,
                "Completa todos los campos",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val resultado = dbHelper.guardarEquipo(
            nombre,
            tipo,
            marca,
            serie,
            estado
        )

        if (resultado != -1L) {

            Toast.makeText(
                this,
                "¡Equipo registrado correctamente!",
                Toast.LENGTH_LONG
            ).show()

            binding.etNombre.text.clear()
            binding.etMarca.text.clear()
            binding.etSerie.text.clear()
            binding.spTipo.setSelection(0)
            binding.spEstado.setSelection(0)

        } else {

            Toast.makeText(
                this,
                "No se pudo guardar el equipo",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }
}