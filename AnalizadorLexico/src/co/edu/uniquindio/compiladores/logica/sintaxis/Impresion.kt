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

}