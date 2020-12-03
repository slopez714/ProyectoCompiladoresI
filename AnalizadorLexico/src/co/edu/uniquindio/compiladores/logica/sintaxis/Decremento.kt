package co.edu.uniquindio.compiladores.logica.sintaxis

import co.edu.uniquindio.compiladores.logica.lexico.Error
import co.edu.uniquindio.compiladores.logica.lexico.Token
import co.edu.uniquindio.compiladores.logica.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Decremento(var identificador: Token?, var operador: Token?) : Sentencia()  {

    override fun toString(): String {
        return "Decremento(identificador=$identificador , operador=$operador )"
    }
    override fun getArbolVisual(): TreeItem<String> {
        var decremento= TreeItem("Incremento")
        if(identificador!=null){
            decremento.children.add(TreeItem("Nombre: " +identificador!!.lexema))
        }
        if(operador!=null){
            decremento.children.add(TreeItem("Operador: " +operador!!.lexema))
        }
        return decremento
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemantico: ArrayList<Error>, ambito: String) {
        var simbolo = tablaSimbolos.buscarSimboloValor(identificador!!.lexema, ambito)

        if(simbolo==null){
            erroresSemantico.add(Error("la variable ${identificador!!.lexema} no existe", identificador!!.fila, identificador!!.columna))
        }else if(simbolo.tipo!="entero"||simbolo.tipo!="decimal"){
            erroresSemantico.add(Error("la variable ${identificador!!.lexema} tiene un tipo de dato distinto", identificador!!.fila, identificador!!.columna))
        }
    }
}