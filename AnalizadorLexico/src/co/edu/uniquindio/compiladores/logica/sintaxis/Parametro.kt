package co.edu.uniquindio.compiladores.logica.sintaxis

import co.edu.uniquindio.compiladores.logica.lexico.Token
import javafx.scene.control.TreeItem

class Parametro( var nombre:Token , var tipoDato: Token) {

    override fun toString(): String {
        return "Parametro(nombre=$nombre , TipoDato= $tipoDato"
    }

    fun getArbolVisual(): TreeItem<String> {
        var parametros= TreeItem<String>("Parametro")

        parametros.children.add(TreeItem("Nombre: " + nombre!!.lexema))
        parametros.children.add(TreeItem("Tipo de Dato: " + tipoDato!!.lexema))

        return parametros
    }

    fun getJavaCode():String{
        return tipoDato.lexema + " " + nombre.lexema
    }
}