package co.edu.uniquindio.compiladores.logica.sintaxis

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
}