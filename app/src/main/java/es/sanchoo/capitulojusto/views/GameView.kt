package es.sanchoo.capitulojusto.views

import android.content.Context

interface GameView {    //interfaz deonde declaramos las funciones que corresponden a la vista

    abstract fun getContext(): Context

    // VISTA: actualizar puntuación
    // VISTA: indicar respuesta correcta
    // VISTA: añadir feedback
    // VISTA: borrar feedback
    // VISTA: limpiar capítulos que estaban
    // VISTA: cambiar viñeta / 10

    // ADD: abrir pestaña final
    // ADD: te has quedado sin paneles


}