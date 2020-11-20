package co.edu.uniquindio.compiladores.logica.sintaxis

import javafx.scene.control.TreeItem

open class Sentencia {

    open fun getArbolVisual(): TreeItem<String> {
        var sentencias=TreeItem<String>("Sentencia")

        return sentencias
    }
}