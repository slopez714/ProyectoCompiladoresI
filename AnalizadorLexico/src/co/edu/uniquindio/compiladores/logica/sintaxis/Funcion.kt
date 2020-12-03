package co.edu.uniquindio.compiladores.logica.sintaxis

import co.edu.uniquindio.compiladores.logica.lexico.Token
import co.edu.uniquindio.compiladores.logica.semantica.TablaSimbolos
import co.edu.uniquindio.compiladores.logica.lexico.Error
import co.edu.uniquindio.compiladores.logica.semantica.Simbolo
import javafx.scene.control.TreeItem

class Funcion(var nombreFuncion: Token, var tipoRetorno: Token?, var listaParametros: ArrayList<Parametro>,
              var listaSentencia: ArrayList<Sentencia>) {

    var s:Simbolo? = null

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

    fun obtenerParametros(): ArrayList<String>{
        val lista: ArrayList<String> = ArrayList()
        for(p in listaParametros){
            lista.add(p.nombre.lexema)
        }
        return lista
    }

    fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito:String){

        tablaSimbolos.guardarSimboloFuncion(nombreFuncion.lexema, tipoRetorno!!.lexema, obtenerParametros())

        for(p in listaParametros){
            tablaSimbolos.guardarSimboloValor(p.nombre.lexema, p.tipoDato.lexema, nombreFuncion.lexema, p.nombre.fila, p.nombre.columna)
        }
        for(s in listaSentencia){
            s.llenarTablaSimbolos(tablaSimbolos, erroresSemanticos, nombreFuncion.lexema)
        }
    }

    fun analizarSemantica(tablaSimbolos: TablaSimbolos , erroresSemantica: ArrayList<Error> ) {

        for (s in listaSentencia) {
            s.analizarSemantica(tablaSimbolos, erroresSemantica, nombreFuncion.lexema)
        }

    }
}