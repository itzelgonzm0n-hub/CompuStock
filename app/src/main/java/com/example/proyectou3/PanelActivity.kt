package com.example.proyectou3

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectou3.databinding.ActivityPanelBinding

class PanelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPanelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPanelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCerrarSesion.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.btnRegistrarEquipo.setOnClickListener {
            startActivity(
                Intent(this, RegistrarActivity::class.java)
            )
        }

        binding.btnInventario.setOnClickListener {
            startActivity(Intent(this, InventarioActivity::class.java)
            )
        }
        binding.btnReportes.setOnClickListener {
            startActivity(Intent(this, ReportesActivity::class.java))
        }
    }
}