package co.edu.uniquindio.compiladores.logica.sintaxis

import co.edu.uniquindio.compiladores.logica.lexico.Token
import javafx.scene.control.TreeItem


class Incremento(var identificador: Token?, var operador: Token?) : Sentencia() {

    override fun getArbolVisual(): TreeItem<String> {
        var incremento= TreeItem("Incremento")
        if(identificador!=null){
            incremento.children.add(TreeItem("Nombre: " +identificador))
        }
        if(operador!=null){
            incremento.children.add(TreeItem("Operador: " +operador))
        }
        return incremento
    }
}