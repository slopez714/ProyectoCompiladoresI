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
        var expresionA=TreeItem(expresion!!.getArbolVisual())
        expresionRelacional.children.add(expresionA)
        expresionRelacional.children.add(TreeItem("Operador: "+ operador!!.lexema))
        if(expresion2!=null){
            var expresionB=TreeItem(expresion!!.getArbolVisual())
            expresionRelacional.children.add(expresionB)
        }
        return expresionRelacional
    }
}