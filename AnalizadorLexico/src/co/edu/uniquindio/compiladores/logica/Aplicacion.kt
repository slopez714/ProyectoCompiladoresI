package co.uniquindio.compiladores.logica

import co.edu.uniquindio.compiladores.logica.lexico.AnalizadorLexico
import co.edu.uniquindio.compiladores.logica.sintaxis.AnalizadorSintactico

fun main(){
    val lexico = AnalizadorLexico("123  definir M(sdhsj)(v(a), v(b)) : entero{}")
    lexico.analizar()

    val sintaxis =AnalizadorSintactico(lexico.listaTokens)
    print(sintaxis.esUnidadDeCompilacion())
    print(sintaxis.listaErrores)


}