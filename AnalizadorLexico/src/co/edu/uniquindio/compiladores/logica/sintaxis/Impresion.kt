package co.edu.uniquindio.compiladores.logica.sintaxis

import co.edu.uniquindio.compiladores.logica.lexico.Error
import co.edu.uniquindio.compiladores.logica.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Impresion : Sentencia{
    var expresion : Expresion?= null

    constructor(expresion : Expresion){
        this.expresion=expresion
    }

    override fun getArbolVisual(): TreeItem<String> {
        var impresion = TreeItem<String>("Impresion")
        impresion.children.add(expresion!!.getArbolVisual())

        return impresion
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemantico: ArrayList<Error>, ambito: String) {
        if(expresion!=null){
            expresion!!.analizarSemantica(tablaSimbolos,erroresSemantico,ambito)
        }/*else{
            erroresSemantico.add(Error("No se encuentra nada dendo de la impresion",))
        }*/
    }
}