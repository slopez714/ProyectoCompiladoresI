package co.edu.uniquindio.compiladores.logica.sintaxis

import co.edu.uniquindio.compiladores.logica.lexico.Token
import javafx.scene.control.TreeItem

class DeclaracionVariable : Sentencia {

    var nombre: Token? = null
    var tipoDato: Token?= null

    constructor(nombre: Token?, tipoDato: Token){
        this.nombre=nombre
        this.tipoDato=tipoDato
    }
    override fun toString(): String {
        return "DeclaracionVariable(tipoDato=$tipoDato, nombre=$nombre)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var declaracion= TreeItem<String>("Declaracion Variable")
        declaracion.children.add(TreeItem("Nombre: " +nombre!!.lexema))
        declaracion.children.add(TreeItem("Tipo de Dato: " +tipoDato!!.lexema))
        return declaracion
    }
}