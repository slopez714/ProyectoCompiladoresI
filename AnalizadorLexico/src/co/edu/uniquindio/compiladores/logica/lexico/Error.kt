package co.edu.uniquindio.compiladores.logica.lexico

open class Error(var error : String, var fila : Int, var columna : Int) {

   /* constructor(error : String, nombre:String) {
        this.error=error
        this.nombre=nombre
    }*/

    override fun toString(): String {
        return "Error (error = '$error', fila = $fila , columna = $columna)"
    }

}