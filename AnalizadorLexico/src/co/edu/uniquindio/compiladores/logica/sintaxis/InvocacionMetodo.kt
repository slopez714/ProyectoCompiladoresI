package co.edu.uniquindio.compiladores.logica.sintaxis

import co.edu.uniquindio.compiladores.logica.lexico.Token

class InvocacionMetodo() : Sentencia() {

    var identificador: Token?=null
    var listaArgumento: ArrayList<Argumento>?=null

    constructor(identificador: Token?, listaArgumento: ArrayList<Argumento>?) : this()
    {
        this.identificador=identificador
        this.listaArgumento = listaArgumento
    }

    constructor(identificador : Token) : this()
    {
        this.identificador = identificador
    }
}