package co.edu.uniquindio.compiladores.logica.sintaxis

import co.edu.uniquindio.compiladores.logica.lexico.Token
import javafx.scene.control.TreeItem


class Incremento(var identificador: Token?, var operador: Token?) : Sentencia() {

    override fun toString(): String {
        return "Incremento(identificador=$identificador , operador=$operador )"
    }
    override fun getArbolVisual(): TreeItem<String> {
        var incremento= TreeItem("Incremento")
        if(identificador!=null){
            incremento.children.add(TreeItem("Nombre: " +identificador!!.lexema))
        }
        if(operador!=null){
            incremento.children.add(TreeItem("Operador: " +operador!!.lexema))
        }
        return incremento
    }

}