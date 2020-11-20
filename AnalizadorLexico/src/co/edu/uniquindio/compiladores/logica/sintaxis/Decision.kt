package co.edu.uniquindio.compiladores.logica.sintaxis

import co.edu.uniquindio.compiladores.logica.lexico.Token
import javafx.scene.control.TreeItem

class Decision() : Sentencia()  {
    var condicion: ExpresionRelacional? = null
    var listaSentencia: ArrayList<Sentencia>? =null
    var listaSentencia2 : ArrayList<Sentencia>?= null

    constructor(condicion:ExpresionRelacional,listaSentencia: ArrayList<Sentencia>?){
        this.condicion=condicion
        this.listaSentencia=listaSentencia
    }

    constructor(condicion:ExpresionRelacional, listaSentencia: ArrayList<Sentencia>?, listaSentencia2: ArrayList<Sentencia>?){
        this.condicion=condicion
        this.listaSentencia=listaSentencia
        this.listaSentencia2= listaSentencia2
    }

    override fun getArbolVisual(): TreeItem<String> {
        var decision= TreeItem("Decision")
        var expresion= TreeItem(condicion?.getArbolVisual())
        decision.children.add(expresion)
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

}