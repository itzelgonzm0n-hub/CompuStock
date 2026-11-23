package com.example.proyectou3

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectou3.databinding.ActivityAdministracionBinding

class AdministracionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdministracionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAdministracionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnVolver.setOnClickListener {
            finish()
        }

        binding.btnConsultarEquipos.setOnClickListener {
            Toast.makeText(this, "Consulta de equipos", Toast.LENGTH_SHORT).show()
        }

        binding.btnEditarEquipo.setOnClickListener {
            Toast.makeText(this, "Edición de equipos", Toast.LENGTH_SHORT).show()
        }

        binding.btnEliminarEquipo.setOnClickListener {
            Toast.makeText(this, "Eliminación de equipos", Toast.LENGTH_SHORT).show()
        }

        binding.btnActualizarEstado.setOnClickListener {
            Toast.makeText(this, "Actualización de estado", Toast.LENGTH_SHORT).show()
        }
    }
}