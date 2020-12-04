package co.edu.uniquindio.compiladores.logica.sintaxis

import co.edu.uniquindio.compiladores.logica.lexico.Error
import co.edu.uniquindio.compiladores.logica.semantica.Simbolo
import co.edu.uniquindio.compiladores.logica.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Decision : Sentencia  {
    var condicion: ExpresionRelacional? = null
    var listaSentencia: ArrayList<Sentencia>? =null
    var listaSentencia2 : ArrayList<Sentencia>?= null

    constructor(condicion: ExpresionRelacional, listaSentencia: ArrayList<Sentencia>?){
        this.condicion=condicion
        this.listaSentencia=listaSentencia
    }

    constructor(condicion: ExpresionRelacional, listaSentencia: ArrayList<Sentencia>?, listaSentencia2: ArrayList<Sentencia>?){
        this.condicion=condicion
        this.listaSentencia=listaSentencia
        this.listaSentencia2= listaSentencia2
    }

    override fun getArbolVisual(): TreeItem<String> {
        var decision= TreeItem("Decision")
        decision.children.add(condicion!!.getArbolVisual())
        if(listaSentencia!=null){
            var sentencias1= TreeItem("Sentencia")
            for(s in listaSentencia!!){
                sentencias1.children.add(s.getArbolVisual())
            }
            decision.children.add(sentencias1)
        }
        if(listaSentencia2!=null){
            var sentencias2= TreeItem("Sentencia")
            for(s in listaSentencia2!!){
                sentencias2.children.add(s.getArbolVisual())
            }
            decision.children.add(sentencias2)
        }

        return decision
    }

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito :String) {
        for(s in listaSentencia!!){
            s.llenarTablaSimbolos(tablaSimbolos,erroresSemanticos,ambito)
        }
        if(listaSentencia2!=null){
            for(sen in listaSentencia2!!){
                sen.llenarTablaSimbolos(tablaSimbolos,erroresSemanticos,ambito)
            }
        }
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemantico: ArrayList<Error>, ambito: String) {
        if(condicion!=null){
            condicion!!.analizarSemantica(tablaSimbolos,erroresSemantico,ambito)
        }

        for(s in listaSentencia!!){
            s.analizarSemantica(tablaSimbolos, erroresSemantico, ambito)
        }
        if(listaSentencia2!=null){
            for(sen in listaSentencia2!!){
                sen.analizarSemantica(tablaSimbolos, erroresSemantico, ambito)
            }
        }
    }

    override fun getJavaCode(): String {
        var sentenciaIf= "if("
        if(condicion!=null){
            sentenciaIf += condicion!!.getJavaCode() + "){"
            if(listaSentencia!=null){
                for(s in listaSentencia!!){
                    sentenciaIf += s.getJavaCode() + " "
                }
            }
            sentenciaIf+="}"
            if(listaSentencia2!=null){
                sentenciaIf+="else{"
                for(s in listaSentencia2!!){
                    sentenciaIf += s.getJavaCode() + " "
                }
                sentenciaIf += "}"
            }
            return sentenciaIf
        }
        return ""
    }
}