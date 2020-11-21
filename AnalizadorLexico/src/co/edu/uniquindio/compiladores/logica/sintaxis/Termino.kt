package co.edu.uniquindio.compiladores.logica.sintaxis

import co.edu.uniquindio.compiladores.logica.lexico.Token
import javafx.scene.control.TreeItem

class Termino(var termino: Token) {

    fun getArbolVisual() : TreeItem<String>{
        var termino= TreeItem<String>("Termino: "+ termino!!.lexema)

        return termino
    }
}