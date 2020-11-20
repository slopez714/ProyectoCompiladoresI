package co.edu.uniquindio.compiladores.logica.sintaxis

import co.edu.uniquindio.compiladores.logica.lexico.Token

class Importacion () : Sentencia(){
    var paquete: Paquete?= null
    var nombre: Token? = null

    constructor(paquete: Paquete, nombre: Token?){
        this.paquete=paquete
        this.nombre=nombre
    }

    constructor(paquete: Paquete){
        this.paquete=paquete
    }
}