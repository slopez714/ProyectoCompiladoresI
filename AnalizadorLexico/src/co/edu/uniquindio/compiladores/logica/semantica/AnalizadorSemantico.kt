package co.edu.uniquindio.compiladores.logica.semantica

import co.edu.uniquindio.compiladores.logica.lexico.Error
import co.edu.uniquindio.compiladores.logica.sintaxis.UnidadDeCompilacion

class AnalizadorSemantico(val uc:UnidadDeCompilacion) {
    var erroresSemanticos: ArrayList<Error> = ArrayList()
    var tablaSimbolos: TablaSimbolos = TablaSimbolos(erroresSemanticos)
    fun llenarTablaSimbolos() {
        uc.llenarTablaSimbolos(tablaSimbolos, erroresSemanticos)
    }
    fun analizarSemantica() {
        uc.analizarSemantica(tablaSimbolos, erroresSemanticos)
    }
}