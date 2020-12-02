package co.edu.uniquindio.compiladores.logica.sintaxis

import co.edu.uniquindio.compiladores.logica.lexico.Error
import co.edu.uniquindio.compiladores.logica.lexico.Token
import co.edu.uniquindio.compiladores.logica.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Importacion  : Sentencia{
    var paquete: Paquete?= null
    var nombre: Token? = null

    constructor(paquete: Paquete, nombre: Token?){
        this.paquete=paquete
        this.nombre=nombre
    }

    constructor(paquete: Paquete){
        this.paquete=paquete
    }

    override fun getArbolVisual(): TreeItem<String> {
        var importacion = TreeItem<String>("Importacion")
        importacion.children.add(paquete!!.getArbolVisual())
        if(nombre!=null){
            importacion.children.add(TreeItem("Nombre: " + nombre!!.lexema))
        }

        return importacion
    }

}