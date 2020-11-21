package co.edu.uniquindio.compiladores.logica.sintaxis

import javafx.scene.control.TreeItem

class Retorno(var expresion: Expresion?) : Sentencia() {
    override fun toString(): String {
        return "Retorno(expresion=$expresion )"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var retorno = TreeItem<String>("Retorno")
        retorno.children.add(expresion!!.getArbolVisual())

        return retorno
    }
}