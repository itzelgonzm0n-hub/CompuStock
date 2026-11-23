package com.example.proyectou3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectou3.databinding.ActivityReportesBinding

class ReportesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReportesBinding
    private lateinit var dbHelper: InventarioDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReportesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = InventarioDBHelper(this)

        cargarReportes()

        binding.btnVolver.setOnClickListener {
            finish()
        }
    }

    private fun cargarReportes() {

        val equipos = dbHelper.obtenerEquipos()

        val total = equipos.size

        val funcionan = equipos.count {
            it.estado == "Funciona"
        }

        val noFuncionan = equipos.count {
            it.estado == "No funciona"
        }

        binding.tvTotalReportes.text =
            "Total de equipos: $total"

        binding.tvFuncionan.text =
            "Equipos que funcionan: $funcionan"

        binding.tvNoFuncionan.text =
            "Equipos que no funcionan: $noFuncionan"
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }
}