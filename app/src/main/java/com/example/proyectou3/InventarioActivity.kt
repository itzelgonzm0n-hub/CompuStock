package com.example.proyectou3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectou3.databinding.ActivityInventarioBinding

class InventarioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInventarioBinding
    private lateinit var dbHelper: InventarioDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInventarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = InventarioDBHelper(this)

        binding.rvInventario.layoutManager =
            LinearLayoutManager(this)

        cargarEquipos()

        binding.btnVolver.setOnClickListener {
            finish()
        }
    }

    private fun cargarEquipos() {

        val equipos = dbHelper.obtenerEquipos()

        binding.rvInventario.adapter = EquipoAdapter(equipos)

        binding.tvTotalEquipos.text =
            "Equipos registrados: ${equipos.size}"

        if (equipos.isEmpty()) {
            binding.tvSinEquipos.visibility = android.view.View.VISIBLE
            binding.rvInventario.visibility = android.view.View.GONE
        } else {
            binding.tvSinEquipos.visibility = android.view.View.GONE
            binding.rvInventario.visibility = android.view.View.VISIBLE
        }
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }
}