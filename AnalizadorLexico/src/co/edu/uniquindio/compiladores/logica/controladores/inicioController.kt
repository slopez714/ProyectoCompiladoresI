package co.edu.uniquindio.compiladores.logica.controladores

import co.edu.uniquindio.compiladores.logica.lexico.AnalizadorLexico
import co.edu.uniquindio.compiladores.logica.lexico.Error
import co.edu.uniquindio.compiladores.logica.lexico.Token
import co.edu.uniquindio.compiladores.logica.sintaxis.AnalizadorSintactico
import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.TextArea
import javafx.scene.control.TreeView
import javafx.scene.control.cell.PropertyValueFactory
import java.net.URL
import java.util.*


class inicioController: Initializable{

    @FXML
    lateinit var codigoFuente: TextArea
    @FXML
    lateinit var tablaTokens: TableView<Token>
    @FXML
    lateinit var colLexema: TableColumn<Token, String>
    @FXML
    lateinit var colCategoria: TableColumn<Token, String>
    @FXML
    lateinit var colFila: TableColumn<Token, Int>
    @FXML
    lateinit var colColumna: TableColumn<Token, Int>
    @FXML
    lateinit var tablaErroresSintacticos: TableView<Error>
    @FXML
    lateinit var colErrorSintaxis: TableColumn<Token, String>
    @FXML
    lateinit var colFilaSintaxis: TableColumn<Token, Int>
    @FXML
    lateinit var colColumnaSintaxis: TableColumn<Token,Int>
    @FXML
    lateinit var arbolVisual: TreeView<String>

    override fun initialize(p0: URL?, p1: ResourceBundle?)
    {
        colLexema.cellValueFactory = PropertyValueFactory( "lexema")
        colCategoria.cellValueFactory = PropertyValueFactory( "categoria")
        colFila.cellValueFactory = PropertyValueFactory( "fila")
        colColumna.cellValueFactory = PropertyValueFactory( "columna")

        colErrorSintaxis.cellValueFactory = PropertyValueFactory( "Error")
        colFilaSintaxis.cellValueFactory = PropertyValueFactory( "fila")
        colColumnaSintaxis.cellValueFactory = PropertyValueFactory( "columna")

    }

    @FXML
    fun analizarCodigo(e: ActionEvent) {
        if(codigoFuente.text.length>0){
            val lexico = AnalizadorLexico(codigoFuente.text)
            lexico.analizar()

            tablaTokens.items = FXCollections.observableArrayList( lexico.listaTokens )

            val sintaxis= AnalizadorSintactico(lexico.listaTokens)
            val uc= sintaxis.esUnidadDeCompilacion()

            tablaErroresSintacticos.items= FXCollections.observableArrayList<Error?>(sintaxis.listaErrores)

            if(uc!=null){
                arbolVisual.root = uc.getArbolVisual()
            }
        }


    }


}

