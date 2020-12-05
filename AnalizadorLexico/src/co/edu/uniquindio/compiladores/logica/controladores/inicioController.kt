package co.edu.uniquindio.compiladores.logica.controladores

import co.edu.uniquindio.compiladores.logica.lexico.AnalizadorLexico
import co.edu.uniquindio.compiladores.logica.lexico.Error
import co.edu.uniquindio.compiladores.logica.lexico.Token
import co.edu.uniquindio.compiladores.logica.semantica.AnalizadorSemantico
import co.edu.uniquindio.compiladores.logica.sintaxis.AnalizadorSintactico
import co.edu.uniquindio.compiladores.logica.sintaxis.UnidadDeCompilacion
import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import java.io.File
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

    private var unidadCompilacion: UnidadDeCompilacion? = null
    private var lexico: AnalizadorLexico? = null
    private var sintaxis: AnalizadorSintactico? = null
    private var semantica: AnalizadorSemantico? = null

    override fun initialize(p0: URL?, p1: ResourceBundle?)
    {
        colLexema.cellValueFactory = PropertyValueFactory( "lexema")
        colCategoria.cellValueFactory = PropertyValueFactory( "categoria")
        colFila.cellValueFactory = PropertyValueFactory( "fila")
        colColumna.cellValueFactory = PropertyValueFactory( "columna")

        colErrorSintaxis.cellValueFactory = PropertyValueFactory( "Error")
        colFilaSintaxis.cellValueFactory = PropertyValueFactory( "fila")
        colColumnaSintaxis.cellValueFactory = PropertyValueFactory( "columna")
        arbolVisual.root= TreeItem("Unidad de compilacion")

    }

    @FXML
    fun analizarCodigo(e: ActionEvent) {
        if(codigoFuente.text.length>0){
            lexico = AnalizadorLexico(codigoFuente.text)
            lexico!!.analizar()

            tablaTokens.items = FXCollections.observableArrayList( lexico!!.listaTokens )

            sintaxis= AnalizadorSintactico(lexico!!.listaTokens)
            unidadCompilacion= sintaxis!!.esUnidadDeCompilacion()

            tablaErroresSintacticos.items= FXCollections.observableArrayList<Error?>(sintaxis!!.listaErrores)

            if(unidadCompilacion!=null){
                arbolVisual.root = unidadCompilacion!!.getArbolVisual()

                semantica= AnalizadorSemantico(unidadCompilacion!!)
                semantica!!.llenarTablaSimbolos()
                semantica!!.analizarSemantica()
                println(semantica!!.tablaSimbolos)
                tablaErroresSintacticos.items= FXCollections.observableArrayList<Error?>(semantica!!.erroresSemanticos)

            }else{
                arbolVisual.root=TreeItem("Unidad de Compilacion")
            }
        }else{
            val alerta = Alert(Alert.AlertType.ERROR)
            alerta.headerText= null
            alerta.contentText="Hay errores lexicos en el codigo fuente"
            alerta.showAndWait()
        }

    }
    @FXML
    fun traducirCodigo(e:ActionEvent){
        if(unidadCompilacion!=null){
            val codigoP = unidadCompilacion!!.getJavaCode()
            print(codigoP)
            File("src/Principal.java").writeText( codigoP )
            try {
                val p = Runtime.getRuntime().exec("javac src/Principal.java")
                p.waitFor()
                Runtime.getRuntime().exec("java Principal", null, File("src"))
            } catch (ea: Exception) {
                ea.printStackTrace()
            }
        }

    }
}

