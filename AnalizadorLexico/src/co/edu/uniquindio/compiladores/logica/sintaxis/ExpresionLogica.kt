package co.edu.uniquindio.compiladores.logica.sintaxis

import co.edu.uniquindio.compiladores.logica.lexico.Token

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


}