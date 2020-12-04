package co.edu.uniquindio.compiladores.logica.sintaxis

import co.edu.uniquindio.compiladores.logica.lexico.Categoria
import co.edu.uniquindio.compiladores.logica.lexico.Token
import co.edu.uniquindio.compiladores.logica.lexico.Error


class AnalizadorSintactico(var listaTokens: ArrayList<Token>) {

    var posicionActual=0
    var tokenActual= listaTokens[0]
    var listaErrores= ArrayList<Error>()

    fun obtenerSiguienteToken(){
        posicionActual++
        if(posicionActual<listaTokens.size){
            tokenActual=listaTokens[posicionActual]
        }
    }

    /**
     * <UnidadDeCompilacion>:: = <ListaFunciones>
     */
    fun esUnidadDeCompilacion(): UnidadDeCompilacion? {
        val listaFunciones : ArrayList<Funcion> = esListaFunciones()
        return if(listaFunciones.size>0){
            UnidadDeCompilacion(listaFunciones)
        }else null
    }

    /**
     * <ListaFunciones> ::= <Funcion>[<ListaFunciones>]
     */
    fun esListaFunciones(): ArrayList<Funcion> {
        var listaFunciones = ArrayList<Funcion>()
        var funcion = esFuncion()
        while (funcion != null) {
            listaFunciones.add(funcion)
            funcion = esFuncion()
        }
        return listaFunciones
    }

    /**
     * <Funcion> :: definir <Metodo> "("[<ListaParametros>]")"":"<TipoRetorno>
     *     definir M(asgasga) ( v(a) : entero)) : entero{}
     */
    fun esFuncion(): Funcion? {
        if (tokenActual.categoria ==Categoria.PALABRARESERVADA && tokenActual.lexema == "definir"){
            obtenerSiguienteToken()
            if (tokenActual.categoria ==Categoria.IDENTIFICADOR_METODO) {
                val nombre = tokenActual
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.PARENTESIS_IZQUIERDO) {
                    obtenerSiguienteToken()
                    val parametros: ArrayList<Parametro> = esListaParametros()
                    if (tokenActual.categoria == Categoria.PARENTESIS_DERECHO) {
                        obtenerSiguienteToken()
                        if (tokenActual.categoria == Categoria.DOS_PUNTOS) {
                            obtenerSiguienteToken()
                            var tipoRetorno: Token? = esTipoRetorno()
                            if (tipoRetorno != null){
                                obtenerSiguienteToken()
                                val bloqueSentencias: ArrayList<Sentencia> = esBloqueSentencias()
                                if (bloqueSentencias != null) {
                                    return Funcion(nombre, tipoRetorno, parametros, bloqueSentencias)
                                } else {
                                    reportarError("Faltó el bloque de sentencias en la función")
                                }
                            }else{
                                reportarError("Falta el tipo de retorno de la función")
                            }
                        }else{
                            reportarError("Faltan los dos puntos")
                        }

                    } else {
                        reportarError("Falta paréntesis derecho")
                    }
                } else {
                    reportarError("Falta paréntesis izquierdo")
                }
            }else{
                reportarError("Falta definir")
            }
        }
        return null
    }

    /**
	 * <ListaDeParametros>::= <Parametro>[","<ListaDeParametros>]
	 */
    fun esListaParametros(): ArrayList<Parametro> {

        val listaParametro= ArrayList<Parametro>()
        var parametro= esParametro()
        while(parametro!=null){
            listaParametro.add(parametro)
            if(tokenActual.categoria==Categoria.COMA){
                obtenerSiguienteToken()
                parametro=esParametro()
            }else{
                parametro=null
            }
        }

        return listaParametro
    }

    /**
     * <Parametro>::= Identificador":"<TipoDato>
     */
    fun esParametro(): Parametro?{
        if(tokenActual.categoria==Categoria.IDENTIFICADOR_VARIABLE){
            val nombre = tokenActual
            obtenerSiguienteToken()
            if(tokenActual.categoria== Categoria.DOS_PUNTOS){
                obtenerSiguienteToken()
                val tipoDato= esTipoRetorno()
                if(tipoDato!=null){
                    obtenerSiguienteToken()
                    return Parametro(nombre,tipoDato)
                }

            }else{
                reportarError("Falta el tipo de dato")
            }
        }else{
            reportarError("Faltan los dos puntos")
        }

        return null
    }

    fun esBloqueSentencias(): ArrayList<Sentencia>{
        if (tokenActual.categoria== Categoria.LLAVE_IZQUIERDA) {
            obtenerSiguienteToken()
            var listaSentencia = ArrayList<Sentencia>()
            var sentencia: Sentencia? = esSentencia()
            while (sentencia != null) {
                listaSentencia.add(sentencia)
                sentencia = esSentencia()
            }
            if(tokenActual.categoria==Categoria.LLAVE_DERECHA){
                obtenerSiguienteToken()
                return listaSentencia
            }
        }
        return ArrayList()
    }

    fun esSentencia(): Sentencia? {
        var s:Sentencia?= null
        val posInicial = posicionActual
        s= esCiclo()
        if(s!=null){
            return s
        }
        s=esDeclaracionVariable()
        if(s!=null){
            return s
        }
        s = esIf()

        if (s != null) {
            return s;
        }

        s = esAsignacion(true)

        if (s != null) {
            return s;
        }

        s = esLectura()

        if (s != null) {
            return s;
        }

        s = esEscritura()

        if (s != null) {
            return s;
        }

        s = esInvocacion()

        if (s != null) {
            return s;
        }

        s = esRetorno()

        if (s != null) {
            return s;
        }

        s = esIncremento()

        if (s != null) {
            return s;
        }

        s = esDecremento()

        if (s != null) {
            return s;
        }

        s= esImportacion()

        if (s != null) {
            return s;
        }

        if(s==null){
            hacerBT(posInicial)
        }

        return s;
    }
    /**
     * <decremento> :: = identificador "." dec ".."
     *
     */
    fun esDecremento(): Sentencia? {
       var posicionInit=posicionActual
       if(tokenActual.categoria==Categoria.IDENTIFICADOR_VARIABLE){
           var nombre: Token? = tokenActual
           obtenerSiguienteToken()
           if(tokenActual.categoria==Categoria.PUNTO){
               obtenerSiguienteToken()
               if(tokenActual.categoria==Categoria.PALABRARESERVADA && tokenActual.lexema=="dec"){
                   var operador: Token? = tokenActual
                   obtenerSiguienteToken()
                   if(tokenActual.categoria==Categoria.FIN_DE_SENTENCIA){
                       obtenerSiguienteToken()
                       return Decremento(nombre, operador)
                   }else{
                       reportarError("Falta fin de la sentencia")
                   }
               }else{
                   hacerBT(posicionInit)
               }
           }
       }
       return null
    }

    /**
     * <Incremento> :: = identificador "." inc ".."
     *
     */
    fun esIncremento(): Sentencia? {
        var posicionInit=posicionActual
        if(tokenActual.categoria==Categoria.IDENTIFICADOR_VARIABLE){
            var nombre: Token? = tokenActual
            obtenerSiguienteToken()
            if(tokenActual.categoria==Categoria.PUNTO){
                obtenerSiguienteToken()
                if(tokenActual.categoria==Categoria.PALABRARESERVADA && tokenActual.lexema=="inc"){
                    var operador: Token? = tokenActual
                    obtenerSiguienteToken()
                    if(tokenActual.categoria==Categoria.FIN_DE_SENTENCIA){
                        obtenerSiguienteToken()
                        return Incremento(nombre, operador)
                    }else{
                        reportarError("Falta fin de la sentencia")
                    }
                }else{
                    hacerBT(posicionInit)
                }
            }
        }
        return null
    }

    /**
	 * <Retorno> ::= retornar <Expresion> ".."
	 */
    fun esRetorno(): Sentencia? {
        if(tokenActual.categoria==Categoria.PALABRARESERVADA && tokenActual.lexema=="retornar"){
            obtenerSiguienteToken()
            var expresion: Expresion? = esExpresion()
            if(tokenActual.categoria==Categoria.FIN_DE_SENTENCIA) {
                obtenerSiguienteToken()
                return Retorno(expresion)
            }else{
                reportarError("Falta fin de la sentencia")
            }
        }
        return null
    }

    /**
	 * <Invocacion> ::= identificador "(" [<ListaArgumentos> ] ")" ".."
	 */
    fun esInvocacion(): Sentencia? {
        if(tokenActual.categoria==Categoria.IDENTIFICADOR_METODO){
            var nombre: Token? = tokenActual
            if(tokenActual.categoria==Categoria.PARENTESIS_IZQUIERDO){
                obtenerSiguienteToken()
                var listaArgumento: ArrayList<Argumento>? = esListaArgumentos()
                if(tokenActual.categoria==Categoria.PARENTESIS_DERECHO){
                    obtenerSiguienteToken()
                    if(tokenActual.categoria==Categoria.FIN_DE_SENTENCIA){
                        obtenerSiguienteToken()
                        return InvocacionMetodo(nombre,listaArgumento)
                    }else{
                        reportarError("Falta fin de l sentencia")
                    }
                }else{
                    reportarError("Falta parentesis derecho")
                }
            }else{
                reportarError("Falta parentesis izquierdo")
            }
        }
        return null
    }

    /**
	 * <ListaArgumentos> ::= <Argumento> ["," <ListaArgumentos> ]
	 */
    fun esListaArgumentos(): ArrayList<Argumento>? {

        var listaArgumento: ArrayList<Argumento>? = null
        var argumento: Argumento?= esArgumento()
        while(argumento!=null){
            listaArgumento?.add(argumento)
            if(tokenActual.categoria==Categoria.COMA){
                obtenerSiguienteToken()
                argumento=esArgumento()
            }else{
                argumento= null
            }
        }
        return listaArgumento
    }

    /**
	 * <Argumento> ::= identificador
	 */
    fun esArgumento(): Argumento? {
        if(tokenActual.categoria==Categoria.IDENTIFICADOR_VARIABLE){
            var identificador : Token? = tokenActual
            obtenerSiguienteToken()
            return Argumento(identificador)
        }else if(tokenActual.lexema!=")"){
            reportarError("Argumento invalido")
        }
        return null
    }

    /**
         * <Escritura> ::= imprimir ":" "(" <expresionCadena> ")" ".."
     */
    fun esEscritura(): Sentencia? {
        if(tokenActual.categoria==Categoria.PALABRARESERVADA && tokenActual.lexema=="imprimir"){
            obtenerSiguienteToken()
            if(tokenActual.categoria==Categoria.DOS_PUNTOS){
                obtenerSiguienteToken()
                if(tokenActual.categoria==Categoria.PARENTESIS_IZQUIERDO){
                    obtenerSiguienteToken()
                    var expresion: Expresion?= esExpresion()
                    if(expresion!=null){
                        if(tokenActual.categoria==Categoria.PARENTESIS_DERECHO){
                            obtenerSiguienteToken()
                            if(tokenActual.categoria==Categoria.FIN_DE_SENTENCIA){
                                obtenerSiguienteToken()
                                return Impresion(expresion)
                            }else{
                                reportarError("Falta fin de la sentencia")
                            }
                        }else{
                            reportarError("Falta parentesis derecho")
                        }
                    }
                }else{
                    reportarError("Falta parentesis izquierdo")
                }
            }else{
                reportarError("Faltan los dos puntos")
            }
        }
        return null
    }


    /**
	 * <Lectura> ::= identificador "{""}" leer ".."
	 */
    fun esLectura(): Sentencia? {
        var posicionInit=posicionActual
        if(tokenActual.categoria==Categoria.IDENTIFICADOR_VARIABLE){
            var identificador: Token? = tokenActual
            obtenerSiguienteToken()
            if(tokenActual.categoria==Categoria.LLAVE_IZQUIERDA){
                obtenerSiguienteToken()
                if(tokenActual.categoria==Categoria.LLAVE_DERECHA){
                    obtenerSiguienteToken()
                    if(tokenActual.categoria==Categoria.PALABRARESERVADA && tokenActual.lexema=="leer"){
                        obtenerSiguienteToken()
                        if(tokenActual.categoria==Categoria.FIN_DE_SENTENCIA){
                            obtenerSiguienteToken()
                            return Lectura(identificador)
                        }else{
                            reportarError("Falta el fin de sentencia")
                        }
                    }else{
                        reportarError("Falta leer")
                    }
                }else{
                    reportarError("Falta llave derecha")
                }
            }else{
                hacerBT(posicionInit)
            }
        }
        return null
    }

    /**
         * <IF> ::= if "(" <Condicion> ")" "{" [<listaSentencias>] "}" [else"{" [<listaSentencias>] "}"]
         */
    fun esIf(): Sentencia? {
        if(tokenActual.categoria==Categoria.PALABRARESERVADA && tokenActual.lexema=="if"){
            obtenerSiguienteToken()
            if(tokenActual.categoria==Categoria.PARENTESIS_IZQUIERDO){
                obtenerSiguienteToken()
                var condicion: ExpresionRelacional? = esExpresionRelacional()
                if(condicion!=null){
                    if(tokenActual.categoria==Categoria.PARENTESIS_DERECHO){
                        obtenerSiguienteToken()
                        if(tokenActual.categoria==Categoria.LLAVE_IZQUIERDA){
                            obtenerSiguienteToken()
                            var listaSentencia: ArrayList<Sentencia>? = esBloqueSentencias()
                            if(tokenActual.categoria==Categoria.LLAVE_DERECHA){
                                obtenerSiguienteToken()
                                if(tokenActual.categoria==Categoria.PALABRARESERVADA && tokenActual.lexema=="else"){
                                    obtenerSiguienteToken()
                                    if(tokenActual.categoria==Categoria.LLAVE_IZQUIERDA){
                                        obtenerSiguienteToken()
                                        var listaSentencias2 : ArrayList<Sentencia>? = esBloqueSentencias()
                                        if(tokenActual.categoria==Categoria.LLAVE_DERECHA){
                                            obtenerSiguienteToken()
                                            return Decision(condicion,listaSentencia,listaSentencias2)
                                        }else{
                                            reportarError("Falta llave derecha del else")
                                        }
                                    }else{
                                        reportarError("Falta llave izquierda del else")
                                    }
                                }else{
                                    return Decision(condicion, listaSentencia)
                                }
                            }else{
                                reportarError("Falta llave derecha del if")
                            }
                        }else{
                            reportarError("Falta llave izquierda del if")
                        }
                    }else{
                        reportarError("Falta parentesis derecho")
                    }
                }else{
                    reportarError("Falta condicion")
                }
            }else{
                reportarError("Falta parentesis izquierdo")
            }
        }
        return null
    }

    /**
         * <DeclaracionVariable> ::= <TipoDeDato> identificador ".."
     */
    fun esDeclaracionVariable(): Sentencia? {
        var tipoDato: Token? = esTipoRetorno()
        if(tipoDato!=null){
            obtenerSiguienteToken()
            if(tokenActual.categoria==Categoria.IDENTIFICADOR_VARIABLE){
                var nombre: Token? = tokenActual
                obtenerSiguienteToken()
                if(tokenActual.categoria==Categoria.FIN_DE_SENTENCIA){
                    obtenerSiguienteToken()
                    return DeclaracionVariable(nombre, tipoDato)
                }else{
                    reportarError("Falta fin de sentencia")
                }
            }else{
                reportarError("Falta identificador de variable")
            }
        }
        return null
    }

    /**
         * <Ciclo> ::= for "(" <Asignacion> ":" <ExpresionRelacional> [inc | dec ] ")"
         * "{" [<listaSentencias>] "}" | mientras "(" <Condicion> ")" "{"
         * [<listaSentencias>] "}"
     */
    fun esCiclo(): Sentencia?{
        if(tokenActual.categoria==Categoria.PALABRARESERVADA && tokenActual.lexema=="for"){
            obtenerSiguienteToken()
            if(tokenActual.categoria==Categoria.PARENTESIS_IZQUIERDO){
                obtenerSiguienteToken()
                var asignacion: AsignacionVariable? = esAsignacion(false)
                if(asignacion!=null){
                    if(tokenActual.categoria==Categoria.DOS_PUNTOS){
                        obtenerSiguienteToken()
                        var expresion= esExpresionRelacional()
                        if(expresion!=null){
                            var incremento:Token?=null
                            if(tokenActual.categoria==Categoria.OPERADOR_INCREMENTO
                                    || tokenActual.categoria==Categoria.OPERADOR_DECREMENTO){
                                incremento=tokenActual
                                obtenerSiguienteToken()
                            }
                            if(tokenActual.categoria==Categoria.PARENTESIS_DERECHO){
                                obtenerSiguienteToken()
                                if(tokenActual.categoria==Categoria.LLAVE_IZQUIERDA){
                                    obtenerSiguienteToken()
                                    var listaSentencias= esBloqueSentencias()
                                    if(tokenActual.categoria==Categoria.LLAVE_DERECHA){
                                        obtenerSiguienteToken()
                                        return Ciclo(asignacion,expresion,incremento,listaSentencias)
                                    }else{
                                        reportarError("Falta llave derecha")
                                    }
                                }else{
                                    reportarError("Falta llave izquierda")
                                }
                            }else{
                                reportarError("Falta parentesis derecho")
                            }
                        }else{
                            reportarError("Falta expresion relacional")
                        }
                    }else{
                        reportarError("Faltan los dos puntos")
                    }
                }else{
                    reportarError("Falta asignacion de la variable")
                }
            }else{
                reportarError("Falta parentesis izquierdo")
            }
        }else if(tokenActual.categoria==Categoria.PALABRARESERVADA && tokenActual.lexema=="while"){
            obtenerSiguienteToken()
            if(tokenActual.categoria==Categoria.PARENTESIS_IZQUIERDO){
                obtenerSiguienteToken()
                var condicion:ExpresionRelacional?= esExpresionRelacional()
                if(condicion!=null){
                    if(tokenActual.categoria==Categoria.PARENTESIS_DERECHO){
                        obtenerSiguienteToken()
                        if(tokenActual.categoria==Categoria.LLAVE_IZQUIERDA){
                            obtenerSiguienteToken()
                            var listaSentencias: ArrayList<Sentencia> = esBloqueSentencias()
                            if(tokenActual.categoria==Categoria.LLAVE_DERECHA){
                                obtenerSiguienteToken()
                                return Ciclo(condicion,listaSentencias)
                            }else{
                                reportarError("Falta llave derecha")
                            }
                        }else{
                            reportarError("Falta llave izquierda")
                        }
                    }else{
                        reportarError("Falta parentesis derecha")
                    }
                }else{
                    reportarError("Falta condicion")
                }
            }else{
                reportarError("Falta parentesis izquierdo")
            }
        }
        return null
    }

    /**
	 * <Asignacion> ::= <Expresion> operadorAsignacion identificador ".."
	 */
    fun esAsignacion(v: Boolean): AsignacionVariable? {
        var posicionInit= posicionActual
        var expresion: Expresion? = esExpresion()
        if(expresion!=null){
            if(tokenActual.categoria==Categoria.OPERADOR_ASIGNACION||tokenActual.categoria==Categoria.OPERADOR_ASIGNACION_SUMA||
                    tokenActual.categoria==Categoria.OPERADOR_ASIGNACION_DIVISION||tokenActual.categoria==Categoria.OPERADOR_ASIGNACION_MODULO||
                    tokenActual.categoria==Categoria.OPERADOR_ASIGNACION_PRODUCTO||tokenActual.categoria==Categoria.OPERADOR_ASIGNACION_RESTA){
                var operador= tokenActual
                obtenerSiguienteToken()
                if(tokenActual.categoria==Categoria.IDENTIFICADOR_VARIABLE){
                    var nombre= tokenActual
                    if(v==false){
                        obtenerSiguienteToken()
                        return AsignacionVariable(operador, nombre, expresion)
                    }else{
                        obtenerSiguienteToken()
                        if(tokenActual.categoria==Categoria.FIN_DE_SENTENCIA){
                            obtenerSiguienteToken()
                            return AsignacionVariable(operador, nombre, expresion)
                        }else{
                            reportarError("Falta fin de sentencia")
                        }
                    }

                }else{
                    reportarError("Falta identificador de asignacion")
                }
            }else{
                hacerBT(posicionInit)
            }
        }
        return null
    }

    /**
	 * <Expresion> ::= <ExpresionAritmetica> | <ExpresionRelacional> |<ExpresionLogica> | <ExpresionCadena>
	 */
    fun esExpresion() : Expresion? {
        var e : Expresion? = null
        e= esExpresionLogica()
        if(e!=null){
            return e
        }

        e= esExpresionRelacional()
        if(e!=null){
            return e
        }

        e= esExpresionAritmetica()
        if(e!=null){
            return e
        }

        e= esExpresionCadena()
        if(e!=null){
            return e
        }

        return null
    }

    /**
	 * <ExpresionCadena> ::= cadena "+"<Expresion> ";"
	 */
    private fun esExpresionCadena(): ExpresionCadena? {
        if(tokenActual.categoria==Categoria.CADENA) {
            var cadena: Token? = tokenActual
            obtenerSiguienteToken()
            if(tokenActual.categoria==Categoria.ARITMETICO_SUMA){
                obtenerSiguienteToken()
                var expresion: Expresion?= esExpresion()
                if(expresion!= null){
                    return ExpresionCadena(cadena,expresion)
                }else{
                    reportarError("Falta expresion cadena")
                }
            }else{
                return ExpresionCadena(cadena)
            }
        }
        return null
    }

    /**
	 * <ExpresionLogica> ::= <ExpresionRelacional> && <ExpresionRelacional> |
	 * <ExpresionRelacional> || <ExpresionRelacional> | NOT <ExpresionRelacional> |
	 * <ExpresionRelacional>
	 */
    fun esExpresionLogica():ExpresionLogica? {
        var posicionInit= posicionActual
        var expresion: ExpresionRelacional? = esExpresionRelacional()

        if(expresion!=null){
            if(tokenActual.categoria==Categoria.OPERADOR_LOGICO_O || tokenActual.categoria==Categoria.OPERADOR_LOGICO_Y){
                var operador=tokenActual
                obtenerSiguienteToken()
                var expresion2 = esExpresionRelacional()
                if(expresion2!=null){
                    return ExpresionLogica(expresion, operador,expresion2)
                }else{
                    reportarError("Falta segunda expresion logica")
                }
            }else{
                reportarError("Falta operador logico")
            }
        }else{
            if(tokenActual.categoria==Categoria.OPERADOR_LOGICO_NEGACION){
                var operador= tokenActual
                obtenerSiguienteToken()
                var expresion3= esExpresionRelacional()
                if(expresion3!=null){
                    return ExpresionLogica(expresion3,operador)
                }else{
                    reportarError("Falta expresion")
                }
            }
        }
        hacerBT(posicionInit)
        return null
    }

    /**
	 * <ExpresionRelacional> ::= <ExpresionAritmetica> operadorRelacional <ExpresionAritmetica>
	 */
    fun esExpresionRelacional(): ExpresionRelacional? {
        var posicionInit= posicionActual
        var expresion: ExpresionAritmetica? = esExpresionAritmetica()
        if(expresion!=null){
            if(tokenActual.categoria==Categoria.RELACIONAL_DIFERENTE||tokenActual.categoria==Categoria.RELACIONAL_IGUAL||
                    tokenActual.categoria==Categoria.RELACIONAL_MAYOR||tokenActual.categoria==Categoria.RELACIONAL_MAYOR_IGUAL||
                    tokenActual.categoria==Categoria.RELACIONAL_MENOR||tokenActual.categoria==Categoria.RELACIONAL_MENOR_IGUAL){
                var operador= tokenActual
                obtenerSiguienteToken()
                var expresion2 = esExpresionAritmetica()
                if(expresion2!=null){
                    return ExpresionRelacional(expresion, operador, expresion2)
                }else{
                    reportarError("Falta la segunda expresion aritmetica")
                }
            }else{
                hacerBT(posicionInit)
            }
        }
        return null
    }

    /**
	 * <ExpresionAritmetica> ::= <Termino> | <Termino> operadorAritmetico
     * <ExpresionAritmetica> | "(" <ExpresionAritmetica> ")" | "("
	 * <ExpresionAritmetica> ")" operadorAritmetico <ExpresionAritmetica>
	 */
    fun esExpresionAritmetica(): ExpresionAritmetica? {
        var posicionInit= posicionActual
        var termino: Termino? = esTermino()
        if(termino!=null){
            if(tokenActual.categoria==Categoria.ARITMETICO_SUMA ||tokenActual.categoria==Categoria.ARITMETICO_RESTA ||
                    tokenActual.categoria==Categoria.ARITMETICO_DIVISION || tokenActual.categoria==Categoria.ARITMETICO_MODULO ||
                    tokenActual.categoria==Categoria.ARITMETICO_POTENCIA || tokenActual.categoria==Categoria.ARITMETICO_PRODUCTO){
                var operador=tokenActual
                obtenerSiguienteToken()
                var expresionAritmetica= esExpresionAritmetica()
                if(expresionAritmetica!=null) {
                    return ExpresionAritmetica(termino, operador, expresionAritmetica)
                } else{
                    reportarError("Falta expresion aritmetica")
                }
            }else{
                return ExpresionAritmetica(termino)
            }
        } else{
            if( tokenActual.categoria== Categoria.PARENTESIS_IZQUIERDO){
                obtenerSiguienteToken()
                var expresionAritmetica = esExpresionAritmetica()
                if(expresionAritmetica!=null){
                    if(tokenActual.categoria==Categoria.PARENTESIS_DERECHO){
                        obtenerSiguienteToken()
                        if(tokenActual.categoria==Categoria.ARITMETICO_SUMA ||tokenActual.categoria==Categoria.ARITMETICO_RESTA ||
                                tokenActual.categoria==Categoria.ARITMETICO_DIVISION || tokenActual.categoria==Categoria.ARITMETICO_MODULO ||
                                tokenActual.categoria==Categoria.ARITMETICO_POTENCIA || tokenActual.categoria==Categoria.ARITMETICO_PRODUCTO){
                            var operador= tokenActual
                            obtenerSiguienteToken()
                            var expresionAritmetica2= esExpresionAritmetica()
                            if(expresionAritmetica2!=null){
                                return ExpresionAritmetica(expresionAritmetica,operador,expresionAritmetica2)
                            }else{
                                reportarError("Falta la operacion fuera del parentesis")
                            }
                        }else{
                            return ExpresionAritmetica(expresionAritmetica)
                        }

                    }else{
                        reportarError("Falta el operador aritmetico")
                    }
                }else{
                    reportarError("Falta la expresion aritmetica")
                }
            }
        }
        hacerBT(posicionInit)
        return null
    }

    /**
	 * <Termino> ::= entero | identificador
	 */
    fun esTermino(): Termino? {
        var posicionInit= posicionActual
        if(tokenActual.categoria==Categoria.ENTERO || tokenActual.categoria==Categoria.DECIMAL
                || tokenActual.categoria == Categoria.IDENTIFICADOR_VARIABLE ){
            var termino= tokenActual
            obtenerSiguienteToken()
            if(tokenActual.categoria != Categoria.LLAVE_IZQUIERDA){
                return Termino(termino)
            }else{
                hacerBT(posicionInit)
                return null
            }
        }
        return null
    }

    private fun hacerBT(posicionInit: Int) {
        posicionActual=posicionInit
        tokenActual= listaTokens.get(posicionInit)

    }

    /**
     * <TipoRetorno>::* entero | decimal | boolean | caracter | cadena
     */
    fun esTipoRetorno(): Token?{

        if(tokenActual.categoria==Categoria.PALABRARESERVADA ){
            if(tokenActual.lexema =="entero"|| tokenActual.lexema =="decimal"|| tokenActual.lexema =="bool"||
                    tokenActual.lexema =="caracter"|| tokenActual.lexema =="cad"){
                return tokenActual
            }
        }
        return null
    }

    /**
     * <Importacion>::= import <EsPaquete> [como identificador]".."
     */
    fun esImportacion(): Sentencia? {
        if(tokenActual.categoria==Categoria.FIN_DE_SENTENCIA && tokenActual.lexema == "import"){
            obtenerSiguienteToken()
            var paquete: Paquete? = esPaquete()
            if(paquete!=null){
                obtenerSiguienteToken()
                if(tokenActual.categoria==Categoria.PALABRARESERVADA && tokenActual.lexema == "como"){
                    obtenerSiguienteToken()
                    if(tokenActual.categoria==Categoria.IDENTIFICADOR_VARIABLE){
                        var nombre: Token?= tokenActual
                        obtenerSiguienteToken()
                        if(tokenActual.categoria==Categoria.FIN_DE_SENTENCIA){
                            obtenerSiguienteToken()
                            return Importacion(paquete,nombre)
                        }else{
                            reportarError("falta el fin de la sentencia")
                        }
                    }else{
                        reportarError("Falta el identificador")
                    }
                }else{
                    if(tokenActual.categoria==Categoria.FIN_DE_SENTENCIA){
                        obtenerSiguienteToken()
                        return Importacion(paquete)
                    }else{
                        reportarError("Falta el fin de la sentencia")
                    }
                }
            }else{
                reportarError("Falta el paquete")
            }
        }
        return null
    }

    /**
     * <EsPaquete> ::= paquete<EsDireccion>
     */
    fun esPaquete(): Paquete? {
        if(tokenActual.categoria==Categoria.PALABRARESERVADA && tokenActual.lexema== "paquete"){
            obtenerSiguienteToken()
            var direccion: Direccion? = esDireccion()
            if(direccion!=null){
                obtenerSiguienteToken()
                return Paquete(direccion)
            }
        }
        return null
    }

    /**
     * <EsDireccion>::= direccion"."<EsDireccion> | direccion
     */
    private fun esDireccion(): Direccion? {
        var direccion: Token? = tokenActual
        if(direccion!=null){
            obtenerSiguienteToken()
            if(tokenActual.categoria==Categoria.PUNTO){
                obtenerSiguienteToken()
                var direccion2 : Direccion? =esDireccion()
                return Direccion(direccion,direccion2)
            }else{
                obtenerSiguienteToken()
                return Direccion(direccion)
            }
        }
        return null
    }

    fun reportarError(mensaje: String) {
        listaErrores.add(Error(mensaje,tokenActual.fila, tokenActual.columna))

    }

}

