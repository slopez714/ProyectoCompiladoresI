package co.edu.uniquindio.compiladores.logica.sintaxis

import co.edu.uniquindio.compiladores.logica.lexico.Error
import co.edu.uniquindio.compiladores.logica.lexico.Token
import co.edu.uniquindio.compiladores.logica.semantica.TablaSimbolos
import javafx.scene.control.TreeItem


class AsignacionVariable : Sentencia {

        var tipoDato:Token?=null
        var identificadorVariable: Token?=null
        var expresion : Expresion? = null

    constructor(tipoDato : Token, identificadorVariable: Token, expresion : Expresion)
    {
        this.tipoDato=tipoDato
        this.identificadorVariable=identificadorVariable
        this.expresion=expresion
    }

    constructor(identificadorVariable: Token, expresion : Expresion)
    {
        this.identificadorVariable=identificadorVariable
        this.expresion=expresion
    }

    override fun getArbolVisual(): TreeItem<String> {
       var asignacion = TreeItem<String>("Asignacion de Variable")
        asignacion.children.add(TreeItem("Nombre: " + identificadorVariable!!.lexema))
        if(tipoDato!=null){
           asignacion.children.add(TreeItem("Tipo de Dato: " + tipoDato!!.lexema))
        }
        if(expresion!=null){
            var expresiones = expresion!!.getArbolVisual()
            asignacion.children.add(expresiones)
        }

        return asignacion
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemantico: ArrayList<Error>, ambito: String) {

        var s= tablaSimbolos.buscarSimboloValor(identificadorVariable!!.lexema, ambito)

        if(s==null){
            erroresSemantico.add(Error("El campo ${identificadorVariable!!.lexema} no existe en el ambito $ambito", identificadorVariable!!.fila, identificadorVariable!!.columna))
        }else{
            var tipo = s.tipo
            if(expresion!=null) {
                var tipoExp=expresion!!.obtenerTipo(tablaSimbolos, ambito, erroresSemantico)
                if(tipoExp!=tipo){
                    erroresSemantico.add(Error("El tipo de dato de la expresion $tipoExp no coincide con el tipo de dato del campo ${identificadorVariable!!.lexema}", identificadorVariable!!.fila, identificadorVariable!!.columna))

                }
            }
        }
    }

    /*fun getJavaCode():String{
        return identificadorVariable!!.lexema + " " +
    }*/
}