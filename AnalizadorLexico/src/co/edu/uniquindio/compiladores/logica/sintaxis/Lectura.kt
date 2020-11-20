package co.edu.uniquindio.compiladores.logica.sintaxis

import co.edu.uniquindio.compiladores.logica.lexico.Token

class Lectura() : Sentencia() {
    var identificador : Token? = null

    constructor(identificador: Token?){
        this.identificador=identificador
    }
}