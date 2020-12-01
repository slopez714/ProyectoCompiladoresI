package co.edu.uniquindio.compiladores.logica.semantica

import co.edu.uniquindio.compiladores.logica.lexico.Error

class TablaSimbolos (var listaErrores: ArrayList<Error>){

    var listaSimbolos: ArrayList<Simbolo> = ArrayList()
    /**
     * Permite guardar un símbolo de tipo variable en la tabla de símbolos
     */
    fun guardarSimboloValor(nombre: String, tipo: String, ambito: String, fila: Int,
                               columna: Int): Simbolo? {
        val s = buscarSimboloValor(nombre, ambito)
        if (s == null) {
            val nuevo = Simbolo(nombre, tipo, ambito, fila, columna )
            listaSimbolos.add(nuevo)
            return nuevo
        } else {
            listaErrores.add(Error("El valor $nombre ya existe en el ámbito $ambito", fila, columna))
        }
        return null
    }
    /**
     * Permite guardar un símbolo de tipo función en la tabla de símbolos
     */
    fun guardarSimboloFuncion(nombre: String, tipo: String, tipoParametros: ArrayList<Error>): Simbolo? {
        val s = buscarSimboloFuncion(nombre, tipoParametros)
        if (s == null) {
            val nuevo = Simbolo(nombre, tipo, tipoParametros)
            listaSimbolos.add(nuevo)
            return nuevo
        } else {
            listaErrores.add(Error("La función $nombre $tipoParametros ya existe", s.fila , s.columna))
        }
        return null
    }

    /**
     * Permite buscar un valor en la tabla de simbolos
     */
    fun buscarSimboloValor(nombre: String, ambito: String): Simbolo? {
        for (simbolo in listaSimbolos) {
            if (nombre == simbolo.nombre && ambito == simbolo.ambito) {
                return simbolo
            }
        }
        return null
    }

    /**
     *Permite buscar un metodo o funcion en la tabla de simbolos
     */
    fun buscarSimboloFuncion(nombre: String, tiposParametros: ArrayList<Error>): Simbolo? {
        for (simbolo in listaSimbolos) {
            if (simbolo.tipoParametros != null) {
                if (nombre == simbolo.nombre && tiposParametros == simbolo.tipoParametros) {
                    return simbolo
                }
            }
        }
        return null
    }

    override fun toString(): String {
        return "TablaSimbolos(listaSimbolos=$listaSimbolos)"
    }


}