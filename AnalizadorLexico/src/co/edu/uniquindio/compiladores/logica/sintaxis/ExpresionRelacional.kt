package co.edu.uniquindio.compiladores.logica.sintaxis

import co.edu.uniquindio.compiladores.logica.lexico.Token
import javafx.scene.control.TreeItem

class ExpresionRelacional() : Expresion() {

    var expresion : ExpresionAritmetica?=null
    var operador: Token?=null
    var expresion2 : ExpresionAritmetica? = null

    constructor(expresion : ExpresionAritmetica, operador: Token, expresion2 : ExpresionAritmetica) : this()
    {
        this.expresion=expresion
        this.operador=operador
        this.expresion2=expresion2
    }

    constructor(expresion : ExpresionAritmetica, operador: Token) : this()
    {
        this.expresion=expresion
        this.operador=operador
    }

    override fun getArbolVisual(): TreeItem<String> {
        var expresionRelacional= TreeItem<String>("Expresion Relacional")
        expresionRelacional.children.add(expresion!!.getArbolVisual())
        expresionRelacional.children.add(TreeItem("Operador: "+ operador!!.lexema))
        if(expresion2!=null){
            expresionRelacional.children.add(expresion2!!.getArbolVisual())
        }
        return expresionRelacional
    }
}