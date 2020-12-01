package co.edu.uniquindio.compiladores.logica.sintaxis

import co.edu.uniquindio.compiladores.logica.lexico.Token
import co.edu.uniquindio.compiladores.logica.semantica.TablaSimbolos
import co.edu.uniquindio.compiladores.logica.lexico.Error
import javafx.scene.control.TreeItem

class Funcion(var nombreFuncion: Token, var tipoRetorno: Token?, var listaParametros: ArrayList<Parametro>,
              var listaSentencia: ArrayList<Sentencia>) {

    override fun toString(): String {
        return "Funcion(nombreFuncion=$nombreFuncion, tipoRetorno=$tipoRetorno, listaParametros=$listaParametros, " +
                "listaSentencia=$listaSentencia)"
    }

    fun getArbolVisual():TreeItem<String>{
        var funcion = TreeItem<String> ("funcion")
        funcion.children.add(TreeItem("nombre: " + nombreFuncion!!.lexema))
        funcion.children.add(TreeItem("retorno: " + tipoRetorno!!.lexema))

        var parametros= TreeItem("parametros")
        for(f in  listaParametros){
            parametros.children.add(f.getArbolVisual())
        }
        var sentencias= TreeItem("sentencias")
        for(s in  listaSentencia) {
            sentencias.children.add(s.getArbolVisual())
        }
        funcion.children.add(parametros)
        funcion.children.add(sentencias)

        return funcion
    }

    fun obtenerParametros(): ArrayList<Error>{
        val lista: ArrayList<Error> = ArrayList() //debe incializarla aaaaa ya profe muchas gracias. Listo, chao chao profe
        for(p in listaParametros){
            lista.add(Error(p.tipoDato.lexema, p.tipoDato.fila, p.tipoDato.columna))
        }
        return lista
    }

    fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito:String){

        tablaSimbolos.guardarSimboloFuncion(nombreFuncion.lexema, tipoRetorno!!.lexema, obtenerParametros())
    }
}