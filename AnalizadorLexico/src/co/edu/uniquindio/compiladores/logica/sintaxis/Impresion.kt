package co.edu.uniquindio.compiladores.logica.sintaxis

class Impresion() : Sentencia(){
    var expresion : Expresion?= null

    constructor(expresion : Expresion){
        this.expresion=expresion
    }
}