package co.edu.uniquindio.compiladores.logica.sintaxis

import co.edu.uniquindio.compiladores.logica.lexico.Token

class ExpresionCadena(): Expresion() {
    var cadena: Token? = null
    var expresion: Expresion? = null

    constructor(cadena: Token?, expresion: Expresion){
        this.cadena=cadena
        this.expresion=expresion
    }

    constructor(cadena: Token?){
        this.cadena=cadena
    }
}