package co.edu.uniquindio.compiladores.logica.sintaxis

import co.edu.uniquindio.compiladores.logica.lexico.Token
import javafx.scene.control.TreeItem

class InvocacionMetodo() : Sentencia() {

    var identificador: Token?=null
    var listaArgumento: ArrayList<Argumento>?=null

    constructor(identificador: Token?, listaArgumento: ArrayList<Argumento>?) : this()
    {
        this.identificador=identificador
        this.listaArgumento = listaArgumento
    }

    constructor(identificador : Token) : this()
    {
        this.identificador = identificador
    }

    override fun getArbolVisual(): TreeItem<String> {
        var invocacion = TreeItem<String> ("Invocacion")
        invocacion.children.add(TreeItem("Identificador: " + identificador!!.lexema))
        var argumentos = TreeItem<String>("Argumentos")
        for(a in listaArgumento!!){
            argumentos.children.add(a.getArbolVisual())
        }
        invocacion.children.add(argumentos)

        return invocacion
    }
}