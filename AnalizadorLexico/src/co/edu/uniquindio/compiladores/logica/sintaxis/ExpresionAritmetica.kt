package co.edu.uniquindio.compiladores.logica.sintaxis

import co.edu.uniquindio.compiladores.logica.lexico.Categoria
import co.edu.uniquindio.compiladores.logica.lexico.Error
import co.edu.uniquindio.compiladores.logica.lexico.Token
import co.edu.uniquindio.compiladores.logica.semantica.Simbolo
import co.edu.uniquindio.compiladores.logica.semantica.TablaSimbolos
import javafx.scene.control.TreeItem


class ExpresionAritmetica : Expresion {

    var expresionAritmetica: ExpresionAritmetica? = null
    var termino:Termino? = null
    var operador: Token? = null
    var expresionAritmetica2 : ExpresionAritmetica? = null

    constructor(termino: Termino, operador:Token, expresionAritmetica: ExpresionAritmetica){
        this.termino= termino
        this.operador=operador
        this.expresionAritmetica=expresionAritmetica
    }

    constructor(termino: Termino){
        this.termino= termino
    }

    constructor(expresionAritmetica: ExpresionAritmetica, operador: Token, expresionAritmetica2: ExpresionAritmetica){
        this.expresionAritmetica= expresionAritmetica
        this.operador=operador
        this.expresionAritmetica2= expresionAritmetica2
    }

    constructor(expresionAritmetica: ExpresionAritmetica){
        this.expresionAritmetica=expresionAritmetica
    }

    override fun getArbolVisual(): TreeItem<String> {
        var expresionA = TreeItem("Expresion Aritmetica")

        expresionA.children.add(TreeItem("Operador:" + operador!!.lexema))
        if(termino!=null){
            expresionA.children.add(termino!!.getArbolVisual())
        }
        if(expresionAritmetica!=null){
            expresionA.children.add(expresionAritmetica!!.getArbolVisual())
        }
        if(expresionAritmetica2!=null){
            expresionA.children.add(expresionAritmetica2!!.getArbolVisual())
        }
        return expresionA
    }

    override fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito:String, erroresSemanticos: ArrayList<Error>): String {

        if(termino!=null && expresionAritmetica!=null && expresionAritmetica2==null){

            var tipo1= expresionAritmetica!!.obtenerTipo(tablaSimbolos,ambito, erroresSemanticos)
            var tipo2=""

            if(termino!!.termino.categoria == Categoria.ENTERO){
                tipo2= "entero"
            }else if(termino!!.termino.categoria == Categoria.DECIMAL){
                tipo2= "decimal"
            }else{
                var simbolo = tablaSimbolos.buscarSimboloValor(termino!!.termino.lexema, ambito)
                if(simbolo!=null){
                    tipo2= simbolo.tipo
                }else{
                    erroresSemanticos.add(Error("el campo ${termino!!.termino.lexema} no existe en el ambito $ambito", termino!!.termino.fila, termino!!.termino.columna))
                }
            }

            if(tipo1=="decimal"||tipo2=="decimal"){
                return "decimal"
            }else{
                return "entero"
            }

        }else if(termino!=null && expresionAritmetica==null && expresionAritmetica2==null){

            if(termino!!.termino.categoria == Categoria.ENTERO){
                return "entero"
            }else if(termino!!.termino.categoria == Categoria.DECIMAL){
                return "decimal"
            }else{
                var simbolo = tablaSimbolos.buscarSimboloValor(termino!!.termino.lexema, ambito)
                if(simbolo!=null){
                    return simbolo.tipo
                }else{
                    erroresSemanticos.add(Error("el campo ${termino!!.termino.lexema} no existe en el ambito $ambito", termino!!.termino.fila, termino!!.termino.columna))
                }
            }

        }else if(expresionAritmetica!=null && expresionAritmetica2!=null && termino==null){

            var tipo1= expresionAritmetica!!.obtenerTipo(tablaSimbolos,ambito, erroresSemanticos)
            var tipo2= expresionAritmetica2!!.obtenerTipo(tablaSimbolos,ambito, erroresSemanticos)
            if(tipo1=="decimal"||tipo2=="decimal"){
                return "decimal"
            }else{
                return "entero"
            }

        }else if(expresionAritmetica!=null && expresionAritmetica2==null && termino==null){
            return expresionAritmetica!!.obtenerTipo(tablaSimbolos,ambito, erroresSemanticos)

        }
        return ""
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito:String) {
        if(expresionAritmetica!=null){
            expresionAritmetica!!.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito)
        }

        if(expresionAritmetica2!=null){
            expresionAritmetica2!!.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito)
        }

    }
}


