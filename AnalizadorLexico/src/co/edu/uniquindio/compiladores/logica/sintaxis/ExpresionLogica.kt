package co.edu.uniquindio.compiladores.logica.sintaxis

import co.edu.uniquindio.compiladores.logica.lexico.Error
import co.edu.uniquindio.compiladores.logica.lexico.Token
import co.edu.uniquindio.compiladores.logica.semantica.Simbolo
import co.edu.uniquindio.compiladores.logica.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class ExpresionLogica : Expresion{

    var expresion1 : Expresion?=null
    var operadorLogico: Token?=null
    var expresion2 : Expresion? = null

    constructor(expresion1 : Expresion, operadorLogico: Token, expresion2 : Expresion)
    {
        this.expresion1=expresion1
        this.operadorLogico=operadorLogico
        this.expresion2=expresion2
    }

    constructor(expresion1 : Expresion, operadorLogico: Token)
    {
        this.expresion1=expresion1
        this.operadorLogico=operadorLogico
    }

    override fun getArbolVisual(): TreeItem<String> {
        var expresionLogica = TreeItem<String>("Expresion Logica")
        expresionLogica.children.add(expresion1!!.getArbolVisual())
        expresionLogica.children.add(TreeItem("Operador Logico: "+operadorLogico!!.lexema))
        if(expresion2!=null){
            expresionLogica.children.add(expresion2!!.getArbolVisual())
        }

        return expresionLogica
    }

    override fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito:String,erroresSemanticos: ArrayList<Error>): String {
        return "bool"
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito:String) {
        if(expresion1!=null){
            expresion1!!.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito)
        }

        if(expresion2!=null){
            expresion2!!.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito)
        }
    }

    override fun getJavaCode(): String {
        if(expresion1!=null && expresion2!=null && operadorLogico!=null){
            var operadorL= operadorLogico!!.getJavaCode()
            return expresion1!!.getJavaCode() + " " + operadorL + " " + expresion2!!.getJavaCode()
        }else if(expresion1!=null && expresion2==null && operadorLogico!=null){
            var operadorL= operadorLogico!!.getJavaCode()
            return operadorL + " " + expresion1!!.getJavaCode()
        }else if(expresion1!=null && expresion2==null && operadorLogico==null){
            return expresion1!!.getJavaCode()
        }
        return ""
    }
}