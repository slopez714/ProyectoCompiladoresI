package co.edu.uniquindio.compiladores.logica.sintaxis

import co.edu.uniquindio.compiladores.logica.lexico.Error
import co.edu.uniquindio.compiladores.logica.lexico.Token
import co.edu.uniquindio.compiladores.logica.semantica.Simbolo
import co.edu.uniquindio.compiladores.logica.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class ExpresionCadena: Expresion {
    var cadena: Token? = null
    var expresion: Expresion? = null

    constructor(cadena: Token?, expresion: Expresion){
        this.cadena=cadena
        this.expresion=expresion
    }

    constructor(cadena: Token?){
        this.cadena=cadena
    }

    override fun getArbolVisual(): TreeItem<String> {
        var expresionCadena = TreeItem<String>("Expresion de cadena")
        expresionCadena.children.add(TreeItem("Cadena: "+cadena!!.lexema))
        if(expresion!=null){
            expresionCadena.children.add(expresion!!.getArbolVisual())
        }
        return expresionCadena
    }

    override fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito:String, erroresSemanticos: ArrayList<Error>): String {
        return "cad"
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito:String) {
        if(expresion!=null){
            expresion!!.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito)
        }
    }
}