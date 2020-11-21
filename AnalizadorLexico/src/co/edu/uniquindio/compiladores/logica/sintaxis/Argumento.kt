package co.edu.uniquindio.compiladores.logica.sintaxis

import co.edu.uniquindio.compiladores.logica.lexico.Token
import javafx.scene.control.TreeItem

class Argumento(var identificadorVariable: Token?){

    override fun toString(): String {
        return "Argumento(identificadorVariable=$identificadorVariable)"
    }

    fun getArbolVisual(): TreeItem<String>{
        var argumento = TreeItem<String>("Argumento")
        argumento.children.add(TreeItem("Identificador: " +identificadorVariable!!.lexema))

        return argumento
    }
}