package co.edu.uniquindio.compiladores.logica.sintaxis

import co.edu.uniquindio.compiladores.logica.lexico.Token
import javafx.scene.control.TreeItem

class IdentificadorVariable(var identificador : Token) : Expresion() {

    override fun toString(): String {
        return "IdentificadorVariable(identificador= $identificador )"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var identificadorV = TreeItem<String>("Identificador de variable")
        identificadorV.children.add(TreeItem("Identificador: " +identificador!!.lexema))
        return identificadorV
    }
}