package co.edu.uniquindio.compiladores.logica.sintaxis

import co.edu.uniquindio.compiladores.logica.lexico.Token
import javafx.scene.control.TreeItem

class Lectura() : Sentencia() {
    var identificador : Token? = null

    constructor(identificador: Token?){
        this.identificador=identificador
    }

    override fun getArbolVisual(): TreeItem<String> {
        var lectura = TreeItem<String>("Lectura")
        lectura.children.add(TreeItem("Identificadodr:" +identificador!!.lexema))

        return lectura
    }
}