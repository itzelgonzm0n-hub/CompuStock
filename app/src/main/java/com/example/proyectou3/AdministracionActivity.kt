package com.example.proyectou3

import android.content.Intent
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
            startActivity(Intent(this, EditarEquipoActivity::class.java))
        }

        binding.btnEditarEquipo.setOnClickListener {
            Toast.makeText(this, "Edición de equipos", Toast.LENGTH_SHORT).show()
        }

        binding.btnEliminarEquipo.setOnClickListener {
            startActivity(Intent(this, EliminarEquipoActivity::class.java))
        }

        binding.btnActualizarEstado.setOnClickListener {
            Toast.makeText(this, "Actualización de estado", Toast.LENGTH_SHORT).show()
        }
    }
}