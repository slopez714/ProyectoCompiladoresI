package co.edu.uniquindio.compiladores.logica.sintaxis

import co.edu.uniquindio.compiladores.logica.lexico.Token

class DeclaracionArreglo(var tipoDato : Token, var identificadorVariable: Token) : Sentencia() {

    override fun toString(): String {
        return "DeclaracionArreglo(tipoDato=$tipoDato, identificadorVariable=$identificadorVariable)"
    }
}