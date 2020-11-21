package co.edu.uniquindio.compiladores.logica.sintaxis

import co.edu.uniquindio.compiladores.logica.lexico.Token
import javafx.scene.control.TreeItem

class ExpresionLogica() : Expresion() {

    var expresion1 : Expresion?=null
    var operadorLogico: Token?=null
    var expresion2 : Expresion? = null

    constructor(expresion1 : Expresion, operadorLogico: Token, expresion2 : Expresion) : this()
    {
        this.expresion1=expresion1
        this.operadorLogico=operadorLogico
        this.expresion2=expresion2
    }

    constructor(expresion1 : Expresion, operadorLogico: Token) : this()
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

}