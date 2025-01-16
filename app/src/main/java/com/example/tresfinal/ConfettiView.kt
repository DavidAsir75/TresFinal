package com.example.tresfinal

import android.content.Context
import android.view.View
import android.widget.FrameLayout

class ConfettiView(context: Context) : FrameLayout(context) {

    fun mostrarConfeti() {
        visibility = View.VISIBLE

        // Crea partículas de confeti
        for (i in 0..100) {
            val confetti = View(context).apply {
                layoutParams = LayoutParams(20, 20)
                setBackgroundColor((0xFF000000 + (Math.random() * 0xFFFFFF).toLong()).toInt())
                translationX = (Math.random() * width).toFloat()
                translationY = -50f // Empieza arriba de la pantalla
            }
            addView(confetti)

            // Anima las partículas
            confetti.animate()
                .translationY(height.toFloat()) // Baja hasta el final
                .rotationBy((Math.random() * 360).toFloat()) // Rota aleatoriamente
                .setDuration((1000 + Math.random() * 2000).toLong()) // Duración aleatoria
                .withEndAction { removeView(confetti) } // Elimina la partícula al finalizar
                .start()
        }
    }

    fun reiniciarConfeti() {
        removeAllViews()
        visibility = View.GONE
    }
}
