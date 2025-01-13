package com.example.tresfinal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class InicioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        val etJugadorX = findViewById<EditText>(R.id.etJugadorX)
        val etJugadorO = findViewById<EditText>(R.id.etJugadorO)
        val btnIniciarJuego = findViewById<Button>(R.id.btnIniciarJuego)

        btnIniciarJuego.setOnClickListener {
            val jugadorX = etJugadorX.text.toString().ifEmpty { "Jugador X" }
            val jugadorO = etJugadorO.text.toString().ifEmpty { "Jugador O" }

            // Crea un Intent para ir a MainActivity
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("jugadorX", jugadorX)
                putExtra("jugadorO", jugadorO)
            }

            // Inicia la actividad del juego
            startActivity(intent)
        }
    }
}