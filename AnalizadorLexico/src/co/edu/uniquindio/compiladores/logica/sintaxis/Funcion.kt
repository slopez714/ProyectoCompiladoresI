package co.edu.uniquindio.compiladores.logica.sintaxis

import co.edu.uniquindio.compiladores.logica.lexico.Token
import javafx.scene.control.TreeItem

class Funcion(var nombreFuncion: Token, var tipoRetorno: Token?, var listaParametros: ArrayList<Parametro>,
              var listaSentencia: ArrayList<Sentencia>) {

    override fun toString(): String {
        return "Funcion(nombreFuncion=$nombreFuncion, tipoRetorno=$tipoRetorno, listaParametros=$listaParametros, " +
                "listaSentencia=$listaSentencia)"
    }

    fun getArbolVisual():TreeItem<String>{
        var funcion = TreeItem<String> ("funcion")
        funcion.children.add(TreeItem("nombre: " + nombreFuncion))
        funcion.children.add(TreeItem("retorno: " + tipoRetorno))

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
}