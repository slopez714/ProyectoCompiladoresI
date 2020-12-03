package co.edu.uniquindio.compiladores.logica.lexico


class AnalizadorLexico (var codigoFuente:String){

    var caracterActual = codigoFuente[0]
    var listaTokens = ArrayList<Token>()
    var palabrasResevadas= ArrayList<String>()
    var posicionActual=0
    var finCodigo=0.toChar()
    var filaActual=0
    var columnaActual=0

    fun almacenarToken(lexema:String, categoria: Categoria, fila:Int, columna:Int)=listaTokens.add(
        Token(lexema, categoria, fila, columna)
    )

    fun hacerBT(posicionincial:Int, filaInicial:Int, columnaInicial:Int){
        posicionActual=posicionincial
        filaActual=filaInicial
        columnaActual=columnaInicial

        caracterActual=codigoFuente[posicionActual]
    }

    fun analizar(){
        while(caracterActual != finCodigo){
            if(caracterActual==' ' || caracterActual=='\t'|| caracterActual=='\n'){
                obtenerSiguienteCaracter()
                continue
            }

            //Ahí se quedó en un bucle infinito, por algún autómata que tiene el mismo problema que ya soluciné
            //en el de identificador de clase

            if(esEntero()) continue
            if(esDecimal())continue
            if(esCadena()) continue
            if(esCaracter())continue
            if(esBooleano())continue
            if(esIdentificadorCadena()) continue
            if(esIdentificadorCaracter())continue
            if(esIdentificadorEntero())continue
            if(esIdentificadorDecimal())continue
            if(esIdentificadorBooleano())continue
            if(esSuma())continue
            if(esResta()) continue
            if(esPotencia())continue
            if(esProducto())continue
            if(esDivision())continue
            if(esModulo())continue
            if(esMayor())continue
            if(esMayorIgual())continue
            if(esMenor())continue
            if(esMenorIgual())continue
            if(esDiferente())continue
            if(esIgual())continue
            if(esLogicoY())continue
            if(esLogicoO())continue
            if(esLogicoNegacion())continue
            if(esAsignacion())continue
            if(esAsignacionSuma())continue
            if(esAsignacionResta())continue
            if(esAsignacionProducto())continue
            if(esAsignacionDivision())continue
            if(esAsignacionModulo())continue
            if(esIdentificadorVariable()) continue
            if(esIdentificadorClase())continue
            if(esIdentificadorMetodo())continue
            if(esIdentificadorConstante())continue
            if(esFinDeSentencia())continue
            if(esOperadorIncremento())continue
            if(esOperadorDecremento())continue
            if(esComentarioLinea())continue
            if(esComentarioBloque())continue
            if(esComa())continue
            if(esPunto())continue
            if(esDosPuntos())continue
            if(esParentesisIzq())continue
            if(esParentesisDer())continue
            if(esLlaveIzq())continue
            if(esLlaveDer())continue
            if(esCorcheteIzq())continue
            if(esCorcheteDer())continue
            if(esPalabraReservada())continue

            almacenarToken(lexema = ""+caracterActual, categoria= Categoria.DESCONOCIDO, fila = filaActual, columna = columnaActual)
            obtenerSiguienteCaracter()
        }
    }

    fun esEntero():Boolean{
        if(caracterActual.isDigit() ){
            var lexema = ""
            var filaInicial= filaActual
            var columnaInicial= columnaActual
            var posicionInicial= posicionActual

            lexema+=caracterActual
            obtenerSiguienteCaracter()

            while (caracterActual.isDigit()){
                lexema+=caracterActual
                obtenerSiguienteCaracter()
            }
            if(caracterActual=='.'){
                hacerBT(posicionInicial,filaInicial,columnaInicial)
                return false
            }
            almacenarToken(lexema, Categoria.ENTERO,filaInicial,columnaInicial)
            return true
        }
        return false
    }

    fun esDecimal():Boolean{
        if(caracterActual=='.' || caracterActual.isDigit()){
            var lexema = ""
            var filaInicial= filaActual
            var columnaInicial= columnaActual
            var posicionincial=posicionActual

            if (caracterActual=='.'){
                lexema+=caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual.isDigit() && caracterActual!= '.' && caracterActual!='='){
                    lexema+=caracterActual
                    obtenerSiguienteCaracter()
                }else{
                    hacerBT(posicionincial, filaInicial, columnaInicial)
                    return false
                }

            }else{
                lexema+=caracterActual
                obtenerSiguienteCaracter()
                while (caracterActual.isDigit()){
                    lexema+=caracterActual
                    obtenerSiguienteCaracter()
                }
                if (caracterActual=='.'){
                    lexema+=caracterActual
                    obtenerSiguienteCaracter()
                }else{
                    hacerBT(posicionincial, filaInicial, columnaInicial)
                    return false
                }
            }
            //en cualquier caso, al final se deben concatenar todos los dígitos que haya
            while (caracterActual.isDigit() && caracterActual!='.' && caracterActual!='='){
                lexema+=caracterActual
                obtenerSiguienteCaracter()
            }
            almacenarToken(lexema, Categoria.DECIMAL, filaInicial, columnaInicial)
            return true
        }
        return false
    }

    fun esCadena(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        if (caracterActual == '$') {

            var lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()
            var cont = true

            while (caracterActual != '$' && caracterActual != finCodigo && cont != false) {
                lexema += caracterActual
                if (caracterActual == '\\') {
                    obtenerSiguienteCaracter()
                    if (caracterActual != 'n' && caracterActual != 't' && caracterActual != '\\' && caracterActual != '$' &&
                        caracterActual != 'r' && caracterActual != '|') {
                        cont = false
                    }
                    else
                    {
                        lexema += caracterActual
                    }

                }
                obtenerSiguienteCaracter()
            }
            if (cont == false) {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
            if (caracterActual == '$') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema, Categoria.CADENA, filaInicial, columnaInicial)
                return true
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        return false
    }

    fun esCaracter(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        if (caracterActual == '#') {
            var lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == '\\') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual != 'n' && caracterActual != 't' && caracterActual != '\\' && caracterActual != '#' &&
                    caracterActual != 'r' && caracterActual != '|') {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                } else {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                }
            } else {
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }
            if (caracterActual == '#') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema, Categoria.CARACTER, filaInicial, columnaInicial)
                return true
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        return false
    }

    fun esBooleano(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        if (caracterActual == '|') {
            var lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'V' || caracterActual == 'F') {
                lexema+= caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == '|') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.BOOLEANO, filaInicial, columnaInicial)
                    return true
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        return false
    }

    fun esIdentificadorCadena(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        if (caracterActual == 'c') {
            var lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'a') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'd') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.IDENTIFICADOR_CADENA, filaInicial, columnaInicial)
                    return true
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }

        }
        return false
    }

    fun esIdentificadorCaracter(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        if (caracterActual == 'l') {

            var lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'e') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 't') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.IDENTIFICADOR_CARACTER, filaInicial, columnaInicial)
                    return true
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }

        }
        return false

    }

    fun esIdentificadorEntero(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        if (caracterActual == 'n') {
            var lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'u') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'm') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(
                        lexema, Categoria.IDENTIFICADOR_ENTERO, filaInicial, columnaInicial)
                    return true
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        return false

    }
    fun esIdentificadorDecimal(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        if (caracterActual == 'd') {

            var lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'e') {

                lexema += caracterActual
                obtenerSiguienteCaracter()
                if(caracterActual == 'c'){
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.IDENTIFICADOR_DECIMAL, filaInicial, columnaInicial)
                    return true
                }else{
                    hacerBT(posicionInicial,filaInicial,columnaInicial)
                    return false
                }

            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }

        }
        return false
    }

    fun esIdentificadorBooleano(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        if (caracterActual == 'b') {
            var lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'o') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'o') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'l') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        almacenarToken(lexema, Categoria.IDENTIFICADOR_BOOLEANO, filaInicial, columnaInicial)
                        return true
                    }else{
                        hacerBT(posicionInicial,filaInicial,columnaInicial)
                        return false
                    }
                }else{
                    hacerBT(posicionInicial,filaInicial,columnaInicial)
                    return false
                }
            }else{
                hacerBT(posicionInicial,filaInicial,columnaInicial)
                return false
            }

        }
        return false

    }

    fun esSuma(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        if (caracterActual == '+' ) {
            var lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if(caracterActual=='=' ){
                hacerBT(posicionInicial,filaInicial,columnaInicial)
                return false
            }else{
                almacenarToken(lexema, Categoria.ARITMETICO_SUMA, filaInicial, columnaInicial)
                return true

            }

        }
        return false
    }

    fun esResta(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        if (caracterActual == '-' ) {
            var lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if(caracterActual=='='){
                hacerBT(posicionInicial,filaInicial,columnaInicial)
                return false
            }else{
                almacenarToken(lexema, Categoria.ARITMETICO_RESTA, filaInicial, columnaInicial)
                return true

            }

        }
        return false
    }

    fun esPotencia(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        if (caracterActual == '*') {
            var lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if(caracterActual=='='){
                hacerBT(posicionInicial,filaInicial,columnaInicial)
                return false
            }else{
                almacenarToken(lexema, Categoria.ARITMETICO_POTENCIA, filaInicial, columnaInicial)
                return true

            }

        }
        return false
    }

    fun esProducto(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        if (caracterActual == '*') {
            var lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if(caracterActual=='=' ){
                hacerBT(posicionInicial,filaInicial,columnaInicial)
                return false
            }else{
                almacenarToken(lexema, Categoria.ARITMETICO_PRODUCTO, filaInicial, columnaInicial)
                return true
            }
        }
        return false
    }

    fun esDivision(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        if (caracterActual == '/') {
            var lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if(caracterActual=='='){
                hacerBT(posicionInicial,filaInicial,columnaInicial)
                return false
            }else{
                almacenarToken(lexema, Categoria.ARITMETICO_DIVISION, filaInicial, columnaInicial)
                return true

            }

        }
        return false
    }

    fun esModulo(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        if (caracterActual == '%') {
            var lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if(caracterActual=='='){
                hacerBT(posicionInicial,filaInicial,columnaInicial)
                return false
            }else{
                almacenarToken(lexema, Categoria.ARITMETICO_MODULO, filaInicial, columnaInicial)
                return true

            }

        }
        return false
    }


    fun esMayor(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        if (caracterActual == '>' ) {
            var lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema, Categoria.RELACIONAL_MAYOR, filaInicial, columnaInicial)
            return true

        }
        return false

    }

    fun esMayorIgual(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        if (caracterActual == '>') {
            var lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == '>') {
                almacenarToken(lexema, Categoria.RELACIONAL_MAYOR_IGUAL, filaInicial, columnaInicial)
                return true
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        return false

    }

    fun esMenor(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        if (caracterActual == '<' ) {
            var lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema, Categoria.RELACIONAL_MENOR, filaInicial, columnaInicial)
            return true

        }
        return false

    }

    fun esMenorIgual(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        if (caracterActual == '<') {
            var lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == '<') {
                almacenarToken(lexema, Categoria.RELACIONAL_MENOR_IGUAL, filaInicial, columnaInicial)
                return true
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        return false

    }
    fun esDiferente(): Boolean {
        if (caracterActual == '!' ) {
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            var lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == '!') { //Acá le faltaba concatenar el caracter y obtener el siguiente
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema, Categoria.RELACIONAL_DIFERENTE, filaInicial, columnaInicial)
                return true
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        return false
    }

    fun esIgual(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        if (caracterActual == '.') {
            var lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == '=') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema, Categoria.RELACIONAL_IGUAL, filaInicial, columnaInicial)
                return true
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        return false

    }

    fun esLogicoY(): Boolean {
        if (caracterActual == '&') {
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            var lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if(caracterActual == '='){
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema, Categoria.OPERADOR_LOGICO_Y, filaInicial, columnaInicial)
                return true
            }else{
                hacerBT(posicionInicial,filaInicial,columnaInicial)
                return false
            }

        }
        return false

    }

    fun esLogicoO(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        if (caracterActual == '@') {
            var lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema, Categoria.OPERADOR_LOGICO_O, filaInicial, columnaInicial)
            return true

        }
        return false

    }

    fun esLogicoNegacion(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        if (caracterActual == '~') {
            var lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema, Categoria.OPERADOR_LOGICO_NEGACION, filaInicial, columnaInicial)
            return true

        }
        return false

    }

    fun esAsignacion(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        if (caracterActual == '=') {
            var lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == '=') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema, Categoria.OPERADOR_ASIGNACION, filaInicial, columnaInicial)
                return true
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }

        }
        return false

    }

    fun esAsignacionSuma(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        if (caracterActual == '=') {
            var lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == '+') {
                lexema += caracterActual
                obtenerSiguienteCaracter()

                if (caracterActual == '=') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.OPERADOR_ASIGNACION_SUMA, filaInicial, columnaInicial)
                    return true
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }

        }
        return false

    }

    fun esAsignacionResta(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        if (caracterActual == '=') {
            var lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == '-') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == '=') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.OPERADOR_ASIGNACION_RESTA, filaInicial, columnaInicial)
                    return true
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }

        }
        return false

    }

    //debe hacer eso de eliminar ese bt para todos los método, pero únicamente en ese caso que le digo
    //el else del primer if
    fun esAsignacionProducto(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        if (caracterActual == '*') {
            var lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == '=') {
                lexema += caracterActual
                obtenerSiguienteCaracter()

                if (caracterActual == '=') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.OPERADOR_ASIGNACION_PRODUCTO, filaInicial, columnaInicial)
                    return true
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }

        }
        return false

    }

    fun esAsignacionDivision(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        if (caracterActual == '/') {
            var lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == '=') {
                lexema += caracterActual
                obtenerSiguienteCaracter()

                if (caracterActual == '=') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.OPERADOR_ASIGNACION_DIVISION, filaInicial, columnaInicial)
                    return true
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }

        }
        return false

    }

    fun esAsignacionModulo(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        if (caracterActual == '%') {
            var lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == '=') {
                lexema += caracterActual
                obtenerSiguienteCaracter()

                if (caracterActual == '=') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.OPERADOR_ASIGNACION_MODULO, filaInicial, columnaInicial
                    )
                    return true
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }

        }
        return false

    }

    fun esIdentificadorVariable():Boolean{
        if(caracterActual=='v' ){
            var lexema = ""
            var filaInicial= filaActual
            var columnaInicial= columnaActual

            lexema+=caracterActual
            obtenerSiguienteCaracter()

            if(caracterActual=='('){
                lexema += caracterActual
                obtenerSiguienteCaracter()

                if(caracterActual.isLetter() || caracterActual=='$' || caracterActual=='_'){
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    while ( (caracterActual.isLetter() ||
                                caracterActual=='$' ||
                                caracterActual=='_' ||
                                caracterActual.isDigit()) && caracterActual!= finCodigo){
                        lexema+=caracterActual
                        obtenerSiguienteCaracter()
                    }
                    if(caracterActual == ')'){
                        lexema+= caracterActual
                        obtenerSiguienteCaracter()
                        almacenarToken(lexema, Categoria.IDENTIFICADOR_VARIABLE,filaInicial,columnaInicial)
                        return true
                    }
                }else{
                    hacerBT(posicionActual,filaInicial,columnaInicial)
                    return false
                }
            }else{
                hacerBT(posicionActual,filaInicial,columnaInicial)
                return false
            }

        }
        return false
    }

    fun esIdentificadorClase(): Boolean {
        //lo que pasa es que veo que usted siempre en el else del primer if hace un bt
        if (caracterActual == 'C') {
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            var lexema = ""

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == '(') {
                lexema += caracterActual
                obtenerSiguienteCaracter()

                while ( (caracterActual.isLetter() ||
                            caracterActual=='$' ||
                            caracterActual=='_' ||
                            caracterActual.isDigit()) && caracterActual != finCodigo) {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                }
                if (caracterActual == ')') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.IDENTIFICADOR_CLASE, filaInicial, columnaInicial)
                    return true
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        return false

    }

    fun esIdentificadorMetodo(): Boolean {
        if (caracterActual == 'M') {
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            var lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == '(') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if(caracterActual.isLetter() || caracterActual=='$' || caracterActual=='_'){
                    while (caracterActual != finCodigo && caracterActual != ')' && caracterActual != ' ') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                    }
                    if (caracterActual == ')') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        almacenarToken(lexema, Categoria.IDENTIFICADOR_METODO, filaInicial, columnaInicial)
                        return true
                    } else {

                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                }else{
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }

            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        return false
    }

    fun esIdentificadorConstante(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        if (caracterActual == 'c') {

            var lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == 'o') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == '(') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual.isLetter() || caracterActual == '$' || caracterActual == '_') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()

                        while ( (caracterActual.isLetter() || caracterActual == '$' || caracterActual == '_') && caracterActual != finCodigo) {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                        }

                        if (caracterActual == ')') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            almacenarToken(lexema, Categoria.IDENTIFICADOR_CONSTANTE, filaInicial, columnaInicial
                            )
                            return true
                        } else {

                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return false
                        }
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        return false

    }

    fun esFinDeSentencia():Boolean{
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        if (caracterActual == '.') {
            var lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if(caracterActual =='.'){
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema, Categoria.FIN_DE_SENTENCIA, filaInicial, columnaInicial)
                return true
            }else{
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        return false
    }

    fun esOperadorIncremento(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        if (caracterActual == '+') {
            var lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == '=') {
                lexema += caracterActual
                obtenerSiguienteCaracter()


                if(caracterActual == '+' ){
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.OPERADOR_INCREMENTO, filaInicial, columnaInicial)
                    return true
                }else{
                    hacerBT(posicionInicial,filaInicial,columnaInicial)
                    return false
                }

            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }

        }
        return false

    }

    fun esOperadorDecremento(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        if (caracterActual == '-') {
            var lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == '=') {
                lexema += caracterActual
                obtenerSiguienteCaracter()

                if(caracterActual == '-'){
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.OPERADOR_DECREMENTO, filaInicial, columnaInicial)
                    return true
                }else{
                    hacerBT(posicionInicial,filaInicial,columnaInicial)
                    return false
                }

            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }

        }
        return false


    }
    fun esComentarioLinea(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        if (caracterActual == '#') {
            var lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()
            var fila = filaActual

            if (caracterActual == '/') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                var cont = true

                while (cont != false) {
                    if (filaActual > fila || caracterActual == finCodigo) {
                        cont = false
                    } else {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                    }
                }
                if (cont == false) {
                    almacenarToken(lexema, Categoria.COMENTARIO_LINEA, filaInicial, columnaInicial)
                    return true
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        return false
    }

    fun esComentarioBloque(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        if (caracterActual == '#') {
            var lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == '*') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                while (caracterActual != '*' && caracterActual != finCodigo) {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                }
                if(caracterActual == '*'){
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if(caracterActual == '#'){
                        lexema += caracterActual
                        almacenarToken(lexema, Categoria.COMENTARIO_BLOQUE, filaInicial, columnaInicial)
                        return true
                    }else{
                        hacerBT(posicionInicial,filaInicial,columnaInicial)
                        return false
                    }
                }else{
                    hacerBT(posicionInicial,filaInicial,columnaInicial)
                    return false
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        } else {
            hacerBT(posicionInicial, filaInicial, columnaInicial)
            return false
        }
    }
    fun esComa(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        if (caracterActual == ',') {
            var lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema, Categoria.COMA, filaInicial, columnaInicial)
            return true
        }
        return false

    }

    fun esPunto(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        if (caracterActual == '.' ) {
            var lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if(caracterActual!='='){
                almacenarToken(lexema, Categoria.PUNTO, filaInicial, columnaInicial)
                return true
            }else{
                hacerBT(posicionInicial,filaInicial,columnaInicial)
            }

        }
        return false

    }

    fun esDosPuntos(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        if (caracterActual == ':') {
            var lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema, Categoria.DOS_PUNTOS, filaInicial, columnaInicial)
            return true
        }
        return false

    }

    fun esParentesisIzq(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        if (caracterActual == '(') {
            var lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema, Categoria.PARENTESIS_IZQUIERDO, filaInicial, columnaInicial)
            return true
        }
        return false

    }

    fun esParentesisDer(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        if (caracterActual == ')') {
            var lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema, Categoria.PARENTESIS_DERECHO, filaInicial, columnaInicial)
            return true
        }
        return false

    }

    fun esLlaveIzq(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        if (caracterActual == '{') {
            var lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema, Categoria.LLAVE_IZQUIERDA, filaInicial, columnaInicial)
            return true
        }
        return false

    }

    fun esLlaveDer(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        if (caracterActual == '}') {
            var lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema, Categoria.LLAVE_DERECHA, filaInicial, columnaInicial)
            return true
        }
        return false

    }

    fun esCorcheteIzq(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        if (caracterActual == '[') {
            var lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema, Categoria.CORCHETE_IZQUIERDO, filaInicial, columnaInicial)
            return true
        }
        return false

    }

    fun esCorcheteDer(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        if (caracterActual == ']') {
            var lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema, Categoria.CORCHETE_DERECHO, filaInicial, columnaInicial)
            return true
        }
        return false

    }

    fun esPalabraReservada():Boolean{
        llenarPalabras()
        var palabra=""
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual

        while(caracterActual.isLetter()){
            palabra+=caracterActual
            obtenerSiguienteCaracter()
        }

        if(palabrasResevadas.contains(palabra)){
            almacenarToken(palabra, Categoria.PALABRARESERVADA, filaInicial, columnaInicial)
            return true
        }



        return false
    }

    fun llenarPalabras() {

        palabrasResevadas.add("cad")
        palabrasResevadas.add("caracter")
        palabrasResevadas.add("entero")
        palabrasResevadas.add("decimal")
        palabrasResevadas.add("bool")
        palabrasResevadas.add("noretorna")
        palabrasResevadas.add("v")
        palabrasResevadas.add("f")
        palabrasResevadas.add("inc")
        palabrasResevadas.add("dec")
        palabrasResevadas.add("for")
        palabrasResevadas.add("while")
        palabrasResevadas.add("if")
        palabrasResevadas.add("else")
        palabrasResevadas.add("retornar")
        palabrasResevadas.add("definir")
        palabrasResevadas.add("leer")
        palabrasResevadas.add("imprimir")
        palabrasResevadas.add("retorno")
        palabrasResevadas.add("publico")
        palabrasResevadas.add("privado")
        palabrasResevadas.add("null")
        palabrasResevadas.add("como")
        palabrasResevadas.add("romper")
        palabrasResevadas.add("paquete")
        palabrasResevadas.add("importar")

    }

    fun obtenerSiguienteCaracter(){
        if(posicionActual==codigoFuente.length-1){
            caracterActual=finCodigo
        }else{
            if(caracterActual == '\n'){
                filaActual++
                columnaActual=0
            }else{
                columnaActual++
            }
            posicionActual++
            caracterActual=codigoFuente[posicionActual]
        }
    }

}


