package com.example.proyectou3

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.example.proyectou3.databinding.ActivityMainBinding
import kotlin.jvm.java

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var biometricPrompt: BiometricPrompt

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configurarHuella()

        binding.btnHuella.setOnClickListener {
            iniciarAutenticacion()
        }
    }

    private fun configurarHuella() {

        val executor = ContextCompat.getMainExecutor(this)

        biometricPrompt = BiometricPrompt(
            this,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)

                    Toast.makeText(
                        this@MainActivity,
                        "Acceso autorizado",
                        Toast.LENGTH_SHORT
                    ).show()

                    val intent = Intent(
                        this@MainActivity,
                        PanelActivity::class.java
                    )

                    startActivity(intent)
                    finish()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()

                    binding.tvMensaje.text =
                        "Huella no reconocida. Intenta nuevamente."
                }

                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)

                    when (errorCode) {

                        BiometricPrompt.ERROR_LOCKOUT -> {
                            binding.tvMensaje.text =
                                "Demasiados intentos. Espera antes de intentar nuevamente."
                        }

                        BiometricPrompt.ERROR_LOCKOUT_PERMANENT -> {
                            binding.tvMensaje.text =
                                "Acceso bloqueado por seguridad. Desbloquea tu dispositivo."
                        }

                        BiometricPrompt.ERROR_USER_CANCELED,
                        BiometricPrompt.ERROR_NEGATIVE_BUTTON -> {
                            binding.tvMensaje.text =
                                "Autenticación cancelada."
                        }

                        else -> {
                            binding.tvMensaje.text = errString
                        }
                    }
                }
            }
        )
    }

    private fun iniciarAutenticacion() {

        val biometricManager = BiometricManager.from(this)

        val resultado = biometricManager.canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_STRONG
        )

        when (resultado) {

            BiometricManager.BIOMETRIC_SUCCESS -> {

                val promptInfo = BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Acceso a CompuStock")
                    .setSubtitle("Identifícate con tu huella dactilar")
                    .setDescription("Autentícate para administrar el inventario")
                    .setNegativeButtonText("Cancelar")
                    .build()

                biometricPrompt.authenticate(promptInfo)
            }

            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                mostrarMensaje("Este dispositivo no tiene sensor biométrico.")
            }

            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                mostrarMensaje("El sensor biométrico no está disponible.")
            }

            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                mostrarMensaje("No hay huellas registradas en el dispositivo.")
            }

            else -> {
                mostrarMensaje("No se puede utilizar la autenticación biométrica.")
            }
        }
    }

    private fun mostrarMensaje(mensaje: String) {

        binding.tvMensaje.text = mensaje

        Toast.makeText(
            this,
            mensaje,
            Toast.LENGTH_LONG
        ).show()
    }
}


