package co.edu.uniquindio.compiladores.logica.sintaxis

import co.edu.uniquindio.compiladores.logica.lexico.Token

class Argumento(var identificadorVariable: Token?){

    override fun toString(): String {
        return "Argumento(identificadorVariable=$identificadorVariable)"
    }
}