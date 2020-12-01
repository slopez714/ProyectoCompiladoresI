package co.edu.uniquindio.compiladores.logica.sintaxis

import co.edu.uniquindio.compiladores.logica.lexico.Error
import co.edu.uniquindio.compiladores.logica.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class UnidadDeCompilacion( var listaFunciones: ArrayList<Funcion>) {

    override fun toString(): String {
        return "UnidadDeCompilacion(listaFunciones=$listaFunciones)"
    }

    fun getArbolVisual(): TreeItem<String> {
        val raiz = TreeItem("Unidad de compilación")
        for (funcion in listaFunciones) {
            raiz.children.add(funcion.getArbolVisual())
        }
        return raiz
    }

   /** fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>){
        for (f in listaFunciones) {
            f.llenarTablaSimbolos(tablaSimbolos, erroresSemanticos, "unidadCompilacion")
        }
    }*/

    fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>){

    }
}