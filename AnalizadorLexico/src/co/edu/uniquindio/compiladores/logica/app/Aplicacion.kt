package co.edu.uniquindio.compiladores.logica.app

import javafx.application.Application
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

class Aplicacion : Application(){

    @FXML
    override fun start(primaryStage: Stage?) {

        val loader= FXMLLoader(Aplicacion::class.java.getResource("/inicio.fxml"))
        val parent:Parent= loader.load()

        val scene=Scene(parent)
        primaryStage?.scene=scene
        primaryStage?.title="Mi Compilador"
        primaryStage?.show()

    }
    companion object{

        @JvmStatic
        fun main(args: Array<String>){
            launch(Aplicacion::class.java)
        }

    }
}