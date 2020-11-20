package co.edu.uniquindio.compiladores.logica.sintaxis

import javafx.scene.control.TreeItem

open class Expresion {

  open fun getArbolVisual(): TreeItem<String> {
   var expresion= TreeItem<String>("Expresion")
   return expresion
  }
}