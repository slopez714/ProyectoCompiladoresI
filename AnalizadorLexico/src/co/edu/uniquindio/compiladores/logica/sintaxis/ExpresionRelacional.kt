package co.edu.uniquindio.compiladores.logica.sintaxis

import co.edu.uniquindio.compiladores.logica.lexico.Error
import co.edu.uniquindio.compiladores.logica.lexico.Token
import co.edu.uniquindio.compiladores.logica.semantica.Simbolo
import co.edu.uniquindio.compiladores.logica.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class ExpresionRelacional : Expresion {

    var expresion : ExpresionAritmetica?=null
    var operador: Token?=null
    var expresion2 : ExpresionAritmetica? = null

    constructor(expresion : ExpresionAritmetica, operador: Token, expresion2 : ExpresionAritmetica)
    {
        this.expresion=expresion
        this.operador=operador
        this.expresion2=expresion2
    }

    constructor(expresion : ExpresionAritmetica, operador: Token)
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

    override fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito:String, erroresSemanticos: ArrayList<Error>): String {
        return "bool"
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito:String) {
        if(expresion!=null){
            expresion!!.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito)
        }

        if(expresion2!=null){
            expresion2!!.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito)
        }
    }

    override fun getJavaCode(): String {
        if(expresion!=null && expresion2!=null && operador!=null){
            var operadorL= operador!!.getJavaCode()
            return expresion!!.getJavaCode() + " " + operadorL + " " + expresion2!!.getJavaCode()
        }else if(expresion!=null && expresion2==null && operador!=null){
            var operadorL= operador!!.getJavaCode()
            return operadorL + " " + expresion!!.getJavaCode()
        }else if(expresion!=null && expresion2==null && operador==null){
            return expresion!!.getJavaCode()
        }
        return ""
    }
}