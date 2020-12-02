package co.edu.uniquindio.compiladores.logica.sintaxis

import co.edu.uniquindio.compiladores.logica.lexico.Error
import co.edu.uniquindio.compiladores.logica.lexico.Token
import co.edu.uniquindio.compiladores.logica.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Ciclo : Sentencia{
    var asignacion: AsignacionVariable?= null
    var expresion: ExpresionRelacional?= null
    var incremento: Token?= null
    var listaSentencia: ArrayList<Sentencia>? = null

    constructor(asignacion: AsignacionVariable, expresion: ExpresionRelacional, incremento: Token?, listaSentencia: ArrayList<Sentencia>){
        this.asignacion=asignacion
        this.expresion=expresion
        this.incremento=incremento
        this.listaSentencia=listaSentencia
    }
    constructor(expresion : ExpresionRelacional, listaSentencia: ArrayList<Sentencia>){
        this.expresion=expresion
        this.listaSentencia=listaSentencia
    }

    override fun getArbolVisual(): TreeItem<String> {
        var ciclo= TreeItem<String>("Ciclo")

        if(asignacion!=null){
            ciclo.children.add(asignacion!!.getArbolVisual())
        }
        if(expresion!=null){
            ciclo.children.add(expresion!!.getArbolVisual())
        }
        if(incremento!=null){
            ciclo.children.add(TreeItem("Incremento: " + incremento!!.lexema))
        }
        var sentencias= TreeItem("Sentencia")
        for(s in listaSentencia!!){
            sentencias.children.add(s.getArbolVisual())
        }
        ciclo.children.add(sentencias)

        return ciclo
    }

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: String) {
        for(s in listaSentencia!!){
            s.llenarTablaSimbolos(tablaSimbolos, erroresSemanticos, ambito)
        }
    }
}