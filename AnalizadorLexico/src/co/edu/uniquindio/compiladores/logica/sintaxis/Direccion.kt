package co.edu.uniquindio.compiladores.logica.sintaxis

import co.edu.uniquindio.compiladores.logica.lexico.Token
import javafx.scene.control.TreeItem

class Direccion() {
    var direccion1: Token? = null
    var direccion2: Direccion? = null

    constructor(direccion: Token, direccion2: Direccion?){
        this.direccion1=direccion
        this.direccion2=direccion2
    }

    constructor(direccion: Token){
        this.direccion1=direccion
    }

    fun getArbolVisual() : TreeItem<String>{
        var direccion = TreeItem<String>("Direccion")
        direccion.children.add(TreeItem("Direccion: "+direccion1!!.lexema))
        if(direccion2!=null){
            direccion.children.add(direccion2!!.getArbolVisual())
        }

        return direccion
    }
}