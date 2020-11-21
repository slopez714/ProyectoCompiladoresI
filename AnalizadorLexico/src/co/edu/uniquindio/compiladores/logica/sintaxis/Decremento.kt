package co.edu.uniquindio.compiladores.logica.sintaxis

import co.edu.uniquindio.compiladores.logica.lexico.Token
import javafx.scene.control.TreeItem

class Decremento(var identificador: Token?, var operador: Token?) : Sentencia()  {

    override fun toString(): String {
        return "Decremento(identificador=$identificador , operador=$operador )"
    }
    override fun getArbolVisual(): TreeItem<String> {
        var decremento= TreeItem("Incremento")
        if(identificador!=null){
            decremento.children.add(TreeItem("Nombre: " +identificador!!.lexema))
        }
        if(operador!=null){
            decremento.children.add(TreeItem("Operador: " +operador!!.lexema))
        }
        return decremento
    }
}