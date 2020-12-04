package co.edu.uniquindio.compiladores.logica.sintaxis

import co.edu.uniquindio.compiladores.logica.lexico.Error
import co.edu.uniquindio.compiladores.logica.lexico.Token
import co.edu.uniquindio.compiladores.logica.semantica.TablaSimbolos
import javafx.scene.control.TreeItem


class Incremento(var identificador: Token?, var operador: Token?) : Sentencia() {

    override fun toString(): String {
        return "Incremento(identificador=$identificador , operador=$operador )"
    }
    override fun getArbolVisual(): TreeItem<String> {
        var incremento= TreeItem("Incremento")
        if(identificador!=null){
            incremento.children.add(TreeItem("Nombre: " +identificador!!.lexema))
        }
        if(operador!=null){
            incremento.children.add(TreeItem("Operador: " +operador!!.lexema))
        }
        return incremento
    }
    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemantico: ArrayList<Error>, ambito: String) {
        var simbolo = tablaSimbolos.buscarSimboloValor(identificador!!.lexema, ambito)

        if(simbolo==null){
            erroresSemantico.add(Error("la variable ${identificador!!.lexema} no existe", identificador!!.fila, identificador!!.columna))
        }else if(simbolo.tipo!="entero"||simbolo.tipo!="decimal"){
            erroresSemantico.add(Error("la variable ${identificador!!.lexema} tiene un tipo de dato distinto", identificador!!.fila, identificador!!.columna))
        }
    }

    override fun getJavaCode(): String {
        return identificador!!.getJavaCode() + operador!!.getJavaCode() + ";"
    }
}