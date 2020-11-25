package co.edu.uniquindio.compiladores.logica.sintaxis

import co.edu.uniquindio.compiladores.logica.lexico.Token
import javafx.scene.control.TreeItem


class AsignacionVariable : Sentencia {

        var tipoDato:Token?=null
        var identificadorVariable: Token?=null
        var expresion : Expresion? = null

    constructor(tipoDato : Token, identificadorVariable: Token, expresion : Expresion)
    {
        this.tipoDato=tipoDato
        this.identificadorVariable=identificadorVariable
        this.expresion=expresion
    }

    constructor(identificadorVariable: Token, expresion : Expresion)
    {
        this.identificadorVariable=identificadorVariable
        this.expresion=expresion
    }

    override fun getArbolVisual(): TreeItem<String> {
       var asignacion = TreeItem<String>("Asignacion de Variable")
        asignacion.children.add(TreeItem("Nombre: " + identificadorVariable!!.lexema))
        if(tipoDato!=null){
           asignacion.children.add(TreeItem("Tipo de Dato: " + tipoDato!!.lexema))
        }
        var expresiones = expresion?.getArbolVisual()
        asignacion.children.add(expresiones)
        return asignacion
    }

}