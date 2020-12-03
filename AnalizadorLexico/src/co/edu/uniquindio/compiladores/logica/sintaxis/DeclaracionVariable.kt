package co.edu.uniquindio.compiladores.logica.sintaxis

import co.edu.uniquindio.compiladores.logica.lexico.Error
import co.edu.uniquindio.compiladores.logica.lexico.Token
import co.edu.uniquindio.compiladores.logica.semantica.TablaSimbolos
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

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: String) {
        tablaSimbolos.guardarSimboloValor(nombre!!.lexema, tipoDato!!.lexema, ambito, nombre!!.fila, nombre!!.columna)
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemantico: ArrayList<Error>, ambito: String) {
        super.analizarSemantica(tablaSimbolos, erroresSemantico, ambito)
    }
}