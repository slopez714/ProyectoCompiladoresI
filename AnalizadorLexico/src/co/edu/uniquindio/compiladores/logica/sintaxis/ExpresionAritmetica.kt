package co.edu.uniquindio.compiladores.logica.sintaxis

import co.edu.uniquindio.compiladores.logica.lexico.Error
import co.edu.uniquindio.compiladores.logica.lexico.Token
import co.edu.uniquindio.compiladores.logica.semantica.Simbolo
import co.edu.uniquindio.compiladores.logica.semantica.TablaSimbolos
import javafx.scene.control.TreeItem


class ExpresionAritmetica : Expresion {

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
        expresionA.children.add(TreeItem("Operador:" + operador!!.lexema))
        if(termino!=null){
            expresionA.children.add(termino!!.getArbolVisual())
        }
        if(expresionAritmetica!=null){
            expresionA.children.add(expresionAritmetica!!.getArbolVisual())
        }
        if(expresionAritmetica2!=null){
            expresionA.children.add(expresionAritmetica2!!.getArbolVisual())
        }
        return expresionA
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, s: Simbolo) {
        super.analizarSemantica(tablaSimbolos, erroresSemanticos, s)
    }
}


