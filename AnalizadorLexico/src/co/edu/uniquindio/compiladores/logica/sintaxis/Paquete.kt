package co.edu.uniquindio.compiladores.logica.sintaxis

import co.edu.uniquindio.compiladores.logica.lexico.Token
import javafx.scene.control.TreeItem

class Paquete(){
    var direccion: Direccion? = null

    constructor(direccion: Direccion){
        this.direccion=direccion

    }

    fun getArbolVisual(): TreeItem<String>{
        var paquete = TreeItem<String>("Paquete")
        paquete.children.add(direccion!!.getArbolVisual())

        return paquete
    }
}