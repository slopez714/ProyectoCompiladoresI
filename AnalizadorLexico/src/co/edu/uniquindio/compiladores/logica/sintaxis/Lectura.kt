package co.edu.uniquindio.compiladores.logica.sintaxis

import co.edu.uniquindio.compiladores.logica.lexico.Error
import co.edu.uniquindio.compiladores.logica.lexico.Token
import co.edu.uniquindio.compiladores.logica.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Lectura : Sentencia {
    var identificador : Token? = null
    var tipoGlobal :String? = ""

    constructor(identificador: Token?){
        this.identificador=identificador
    }

    override fun getArbolVisual(): TreeItem<String> {
        var lectura = TreeItem<String>("Lectura")
        lectura.children.add(TreeItem("Identificadodr:" +identificador!!.lexema))

        return lectura
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemantico: ArrayList<Error>, ambito: String) {
        var simbolo= tablaSimbolos.buscarSimboloValor(identificador!!.lexema, ambito)
        if(simbolo==null){
            erroresSemantico.add(Error("la variable ${identificador!!.lexema} no existe", identificador!!.fila, identificador!!.columna))
        }
        tipoGlobal=simbolo!!.tipo
    }

    override fun getJavaCode(): String {
        if(tipoGlobal=="entero"){
            return identificador!!.getJavaCode()+"="+"Integer.parseInt(JOptionPane.showInputDialog(null,\"Escriba aqui\"" + "));"
        }else if(tipoGlobal=="decimal"){
            return identificador!!.getJavaCode()+"="+"Double.parseDouble(JOptionPane.showInputDialog(null,\"Escriba aqui\"" + "));"
        }else if(tipoGlobal=="cad"){
            return identificador!!.getJavaCode()+"="+"JOptionPane.showInputDialog(null,\"Escriba aqui\"" + ");"
        }else if(tipoGlobal=="bool"){
            return identificador!!.getJavaCode()+"="+"JOptionPane.showInputDialog(null,\"Escriba aqui\"" + ");"
        }
        return ""
    }
}