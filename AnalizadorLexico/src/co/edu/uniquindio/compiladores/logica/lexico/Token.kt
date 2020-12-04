package co.edu.uniquindio.compiladores.logica.lexico
import co.edu.uniquindio.compiladores.logica.lexico.Categoria
open class Token (var lexema: String, var categoria: Categoria, var fila:Int, var columna:Int){

    override fun toString(): String {
        return "Token(lexema='$lexema', categoria='$categoria', fila='$fila', columna='$columna')"
    }

    fun getJavaCode():String{
        if(categoria==Categoria.PALABRARESERVADA){
            if(lexema=="decimal"){
                return "double"
            }else if(lexema=="entero"){
                return "int"
            }else if(lexema=="bool"){
                return "boolean"
            }else if(lexema=="cad"){
                return "String"
            }else if(lexema=="caracter"){
                return "char"
            }else if(lexema=="v"){
                return "true"
            }else if(lexema=="f"){
                return "false"
            }else if(lexema=="retornar"){
                return "return"
            }else if(lexema=="publico"){
                return "public"
            }else if(lexema=="privado"){
                return "private"
            }else if(lexema=="romper"){
                return "break"
            }else if(lexema=="paquete"){
                return "package"
            }else if(lexema=="importar"){
                return "import"
            }else if(lexema=="for"){
                return "for"
            }else if(lexema=="while"){
                return "while"
            }else if(lexema=="if"){
                return "if"
            }else if(lexema=="else"){
                return "else"
            }else if(lexema=="noretorna"){
                return "void"
            }else if(lexema=="null"){
                return "null"
            }else if(lexema=="continuar"){
                return "continue"
            }
        }else if(categoria==Categoria.FIN_DE_SENTENCIA) {
            return ";"
        }else if(categoria==Categoria.OPERADOR_ASIGNACION_SUMA){
            return "+="
        }else if(categoria==Categoria.OPERADOR_ASIGNACION_RESTA){
            return "-="
        }else if(categoria==Categoria.OPERADOR_ASIGNACION_MODULO){
            return "%="
        }else if(categoria==Categoria.OPERADOR_ASIGNACION_PRODUCTO){
            return "*="
        }else if(categoria==Categoria.OPERADOR_ASIGNACION_DIVISION){
            return "/="
        }else if(categoria==Categoria.OPERADOR_ASIGNACION){
            return "="
        }else if(categoria==Categoria.OPERADOR_INCREMENTO){
            return "++"
        }else if(categoria==Categoria.OPERADOR_DECREMENTO){
            return "--"
        }else if(categoria==Categoria.OPERADOR_LOGICO_NEGACION){
            return "!"
        }else if(categoria==Categoria.OPERADOR_LOGICO_Y){
            return "&&"
        }else if(categoria==Categoria.OPERADOR_LOGICO_O){
            return "||"
        }else if(categoria==Categoria.RELACIONAL_MAYOR){
            return ">"
        }else if(categoria==Categoria.RELACIONAL_MENOR){
            return "<"
        }else if(categoria==Categoria.RELACIONAL_MAYOR_IGUAL){
            return ">="
        }else if(categoria==Categoria.RELACIONAL_MENOR_IGUAL){
            return ">="
        }else if(categoria==Categoria.RELACIONAL_DIFERENTE){
            return "!="
        }else if(categoria==Categoria.RELACIONAL_IGUAL){
            return "=="
        }else if(categoria==Categoria.CADENA){
            return lexema.replace("$","\"")
        }else if(categoria==Categoria.CARACTER) {
            return lexema.replace("#", "'")
        }else if(categoria==Categoria.PARENTESIS_IZQUIERDO){
            return "("
        }else if(categoria==Categoria.PARENTESIS_DERECHO){
            return ")"
        }else if(categoria==Categoria.LLAVE_IZQUIERDA){
            return "{"
        }else if(categoria==Categoria.LLAVE_DERECHA){
            return "}"
        }else if(categoria==Categoria.CORCHETE_IZQUIERDO){
            return "["
        }else if(categoria==Categoria.CORCHETE_DERECHO){
            return "]"
        }
        return ""
    }
}
