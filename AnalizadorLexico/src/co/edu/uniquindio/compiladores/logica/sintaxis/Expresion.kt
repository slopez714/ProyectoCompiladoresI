package co.edu.uniquindio.compiladores.logica.sintaxis

import co.edu.uniquindio.compiladores.logica.lexico.Error
import co.edu.uniquindio.compiladores.logica.semantica.Simbolo
import co.edu.uniquindio.compiladores.logica.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

open class Expresion {

    open fun getArbolVisual(): TreeItem<String> {
        var expresion= TreeItem<String>("Expresion")
         return expresion
    }

    open fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito:String, erroresSemanticos: ArrayList<Error>) : String{

        return ""
    }
    open fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito:String) {
    }
}