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
        //uc.llenarTablaSimbolos(tablaSimbolos, erroresSemanticos) <<-- esto era lo que hacía que le reportara todos esos errores
        uc.analizarSemantica(tablaSimbolos, erroresSemanticos);
    }
}