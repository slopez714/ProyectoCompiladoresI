package co.edu.uniquindio.compiladores.logica.semantica

import co.edu.uniquindio.compiladores.logica.lexico.Error

class Simbolo {

    var nombre: String = ""
    var tipo: String = ""
    var fila = 0
    var columna = 0
    var ambito: String? = ""
    var tipoParametros: ArrayList<String>? = null

    /**
     * Constructor para un simbolo de tipo valor
     */
    constructor(nombre: String, tipo: String, ambito: String, fila:Int, columna:Int){
        this.nombre = nombre
        this.tipo = tipo
        this.ambito = ambito
        this.fila = fila
        this.columna = columna
    }

    /**
     *Constructor para un simbolo de tipo metodo o funcion
     */
    constructor(nombre: String, tipo: String, tipoParametros: ArrayList<Error>){
        this.nombre = nombre
        this.tipo = tipo
        this.tipoParametros = tipoParametros
    }

    override fun toString(): String {
        if(tipoParametros==null){
            return "Simbolo(nombre='$nombre', tipo='$tipo', fila=$fila, columna=$columna, ambito=$ambito)"
        }else{
            return "Simbolo(nombre='$nombre', tipo='$tipo', ambito=$ambito, tipoParametros=$tipoParametros)"
        }

    }


}