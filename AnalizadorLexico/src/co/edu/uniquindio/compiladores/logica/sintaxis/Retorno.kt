package co.edu.uniquindio.compiladores.logica.sintaxis

import co.edu.uniquindio.compiladores.logica.lexico.Error
import co.edu.uniquindio.compiladores.logica.semantica.TablaSimbolos
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
    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemantico: ArrayList<Error>, ambito: String) {

    }
}