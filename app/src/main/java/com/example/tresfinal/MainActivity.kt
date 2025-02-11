package com.example.tresfinal

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tablero: Array<Array<Button>>
    private lateinit var tvTurno: TextView
    private lateinit var confettiView: ConfettiView
    private var jugadorActual = 'X'
    private var movimientos = 0
    private lateinit var jugadorX: String
    private lateinit var jugadorO: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializa las vistas
        jugadorX = intent.getStringExtra("jugadorX") ?: "Jugador X"
        jugadorO = intent.getStringExtra("jugadorO") ?: "Jugador O"
        tvTurno = findViewById(R.id.tvTurno)
        confettiView = findViewById(R.id.confettiView)
        val gridLayout = findViewById<GridLayout>(R.id.tablero)
        val btnReiniciar = findViewById<Button>(R.id.btnReiniciar)

        // Inicializa el tablero
        tablero = Array(3) { fila ->
            Array(3) { columna ->
                val button = Button(this)
                button.text = ""
                button.textSize = 24f
                button.setOnClickListener { realizarMovimiento(button, fila, columna) }
                gridLayout.addView(button, GridLayout.LayoutParams().apply {
                    width = 0
                    height = 0
                    rowSpec = GridLayout.spec(fila, 1f)
                    columnSpec = GridLayout.spec(columna, 1f)
                })
                button
            }
        }

        // Reinicia el juego
        btnReiniciar.setOnClickListener { reiniciarJuego() }

        // Actualiza el turno inicial
        tvTurno.text = "Turno de $jugadorX"
    }

    private fun realizarMovimiento(boton: Button, fila: Int, columna: Int) {
        if (boton.text.isNotEmpty()) return

        boton.text = jugadorActual.toString()
        movimientos++

        if (verificarGanador()) {
            val ganador = if (jugadorActual == 'X') jugadorX else jugadorO
            mostrarGanador("¡$ganador ha ganado!")
            return
        }

        if (movimientos == 9) {
            mostrarGanador("¡Es un empate!")
            return
        }

        jugadorActual = if (jugadorActual == 'X') 'O' else 'X'
        tvTurno.text = "Turno de ${if (jugadorActual == 'X') jugadorX else jugadorO}"
    }

    private fun verificarGanador(): Boolean {
        for (i in 0..2) {
            if (tablero[i].all { it.text == jugadorActual.toString() }) return true
            if (tablero.map { it[i] }.all { it.text == jugadorActual.toString() }) return true
        }
        if (tablero[0][0].text == jugadorActual.toString() &&
            tablero[1][1].text == jugadorActual.toString() &&
            tablero[2][2].text == jugadorActual.toString()
        ) return true

        if (tablero[0][2].text == jugadorActual.toString() &&
            tablero[1][1].text == jugadorActual.toString() &&
            tablero[2][0].text == jugadorActual.toString()
        ) return true

        return false
    }

    private fun mostrarGanador(mensaje: String) {
        AlertDialog.Builder(this)
            .setTitle("¡Juego Terminado!")
            .setMessage(mensaje)
            .setPositiveButton("OK") { _, _ -> }
            .show()

        confettiView.mostrarConfeti() // Muestra el confeti
    }

    private fun reiniciarJuego() {
        tablero.flatten().forEach {
            it.text = ""
            it.isEnabled = true
        }
        jugadorActual = 'X'
        movimientos = 0
        tvTurno.text = "Turno de $jugadorX"

        // Limpia el confeti
        confettiView.reiniciarConfeti()
    }
}
