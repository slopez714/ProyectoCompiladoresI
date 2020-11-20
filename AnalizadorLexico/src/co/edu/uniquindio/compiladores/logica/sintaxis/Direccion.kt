package co.edu.uniquindio.compiladores.logica.sintaxis

import co.edu.uniquindio.compiladores.logica.lexico.Token

class Direccion() {
    var direccion: Token? = null
    var direccion2: Direccion? = null

    constructor(direccion: Token, direccion2: Direccion?){
        this.direccion=direccion
        this.direccion2=direccion2
    }

    constructor(direccion: Token){
        this.direccion=direccion
    }
}