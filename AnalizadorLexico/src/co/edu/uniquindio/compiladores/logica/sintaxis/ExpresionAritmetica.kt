package co.edu.uniquindio.compiladores.logica.sintaxis

import co.edu.uniquindio.compiladores.logica.lexico.Token
import javafx.scene.control.TreeItem


class ExpresionAritmetica (): Expresion() {

    var expresionAritmetica: ExpresionAritmetica? = null
    var termino:Termino? = null
    var operador: Token? = null
    var expresionAritmetica2 : ExpresionAritmetica? = null

    constructor(termino: Termino, operador:Token, expresionAritmetica: ExpresionAritmetica){
        this.termino= termino
        this.operador=operador
        this.expresionAritmetica=expresionAritmetica
    }

    constructor(termino: Termino){
        this.termino= termino
    }

    constructor(expresionAritmetica: ExpresionAritmetica, operador: Token, expresionAritmetica2: ExpresionAritmetica){
        this.expresionAritmetica= expresionAritmetica
        this.operador=operador
        this.expresionAritmetica2= expresionAritmetica2
    }

    constructor(expresionAritmetica: ExpresionAritmetica){
        this.expresionAritmetica=expresionAritmetica
    }

    override fun getArbolVisual(): TreeItem<String> {
        var expresionA = TreeItem("Expresion Aritmetica")
        expresionA.children.add(TreeItem("Termino:" + termino))
        if(expresionAritmetica!=null){
            var expresionA1=TreeItem(expresionAritmetica!!.getArbolVisual())
            expresionA.children.add(expresionA1)
        }
        if(expresionAritmetica2!=null){
            var expresionA2=TreeItem(expresionAritmetica2!!.getArbolVisual())
            expresionA.children.add(expresionA2)
        }
        return expresionA
    }
}

