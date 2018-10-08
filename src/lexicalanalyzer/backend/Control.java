package lexicalanalyzer.backend;

import java.util.ArrayList;
import lexicalanalyzer.backend.lexemas.TokenValido;
import lexicalanalyzer.backend.lexemas.Error;

public class Control {
    private final char[] ABECEDARIO = {'a','d','g','h','j','k','l',
                            'm','n','o','q','u','x','y','z',
                            'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O',
                            'P','Q','R','S','T','U','V','W','X','Y','Z'};
    private final char[] RESTANTES = {'b','c','e','f','i','p','r','s','t','v','w'};
    private final char[] DIGITOS = {'0','1','2','3','4','5','6','7','8','9'};
    private final char[] SIGNOS_AGRUPACION = {'(',')','{','}','[',']'};
    private final char[] SIGNOS_PUNTUACION = {',','.',';',':'};
    
    private final String ENTERO = "Numero Entero";
    private final String OP_ARITMETICO = "Operador Aritmetico";
    private final String DECIMAL = "Decimal";
    private final String LITERAL = "Literal";
    private final String SIG_AGRUPACION = "Signo de agrupacion";
    private final String SIG_PUNTUACION = "Signo de Puntuacion";
    private final String COMENTARIO1 = "Comentario de una linea";
    private final String COMENTARIO2 = "Comentario de varias lineas";
    private final String IDENTIFICADOR = "Identificador";
    private final String WORD_RESERVADA = "Palabra Reservada";
        
    private final char[] caracteres;
    private String lexema;
    private int fila;
    private int columna;
    private String posicion;
    private ArrayList<TokenValido> tokensValidos = new ArrayList();
    private ArrayList<Error> errores = new ArrayList();
    private int estadoActual;

    public Control(char[] chars) {
        this.caracteres = chars;
        estadoActual = 0;
        lexema = "";
        columna = 0;
        fila = 1;
        
        //En estados que no son finales hay que agregar error al recibir espacio
        for (int i = 0; i < caracteres.length; i++) {
            switch (estadoActual) {
                case -1:
                    
                    break;
                case 0:
                    if (caracteres[i] == '0') {
                        cambioEstado(1, i);
                        posicion = "("+fila+","+columna+")";
                    }else if (caracteres[i] == '+' || caracteres[i] == '-') {
                        cambioEstado(2, i);
                        posicion = "("+fila+","+columna+")";
                    }else if (isNumeroMayorACero(i)) {
                        cambioEstado(4, i);
                        posicion = "("+fila+","+columna+")";
                    }else if (caracteres[i] == '"') {
                        cambioEstado(9, i);
                        posicion = "("+fila+","+columna+")";
                    }else if (isSigPuntuacion(i)) {
                        cambioEstado(12, i);
                        posicion = "("+fila+","+columna+")";
                    }else if (isSigAgrupacion(i)) {
                        cambioEstado(13, i);
                        posicion = "("+fila+","+columna+")";
                    }else if (caracteres[i] == '*' || caracteres[i] == '/' ||
                              caracteres[i] == '%') {
                        cambioEstado(14, i);
                        posicion = "("+fila+","+columna+")";
                    }else if (isOfAbecedario(i) || caracteres[i] == '_') {
                        cambioEstado(22, i);
                        posicion = "("+fila+","+columna+")";
                    }else if (caracteres[i] == 'b') {
                        cambioEstado(24, i);
                        posicion = "("+fila+","+columna+")";
                    }else if (caracteres[i] == 'c') {
                        cambioEstado(31, i);
                        posicion = "("+fila+","+columna+")";
                    }else if (caracteres[i] == 'e') {
                        cambioEstado(37, i);
                        posicion = "("+fila+","+columna+")";
                    }else if (caracteres[i] == 'f') {
                        cambioEstado(47, i);
                        posicion = "("+fila+","+columna+")";
                    }else if (caracteres[i] == 'i') {
                        cambioEstado(62, i);
                        posicion = "("+fila+","+columna+")";
                    }else if (caracteres[i] == 'p') {
                        cambioEstado(84, i);
                        posicion = "("+fila+","+columna+")";
                    }else if (caracteres[i] == 'r') {
                        cambioEstado(109, i);
                        posicion = "("+fila+","+columna+")";
                    }else if (caracteres[i] == 's') {
                        cambioEstado(115, i);
                        posicion = "("+fila+","+columna+")";
                    }else if (caracteres[i] == 't') {
                        cambioEstado(125, i);
                        posicion = "("+fila+","+columna+")";
                    }else if (caracteres[i] == 'v') {
                        cambioEstado(132, i);
                        posicion = "("+fila+","+columna+")";
                    }else if (caracteres[i] == 'w') {
                        cambioEstado(136, i);
                        posicion = "("+fila+","+columna+")";
                    }else if (caracteres[i]  == ' ' || caracteres[i] == '\t') {
                        columna++;
                    }else if (caracteres[i] =='\n') {
                        fila++;
                        columna = 0;
                    }else {
                        lexema += caracteres[i];
                        posicion = "("+fila+","+(columna+1)+")";
                        error();
                    }
                    break;
                case 1:
                    if (caracteres[i] == '.') {
                        cambioEstado(5, i);
                    }else if (caracteres[i]  == ' ' || caracteres[i] == '\t') {
                        estadoFinal(ENTERO);
                    }else if (caracteres[i] == '\n') {
                        estadoFinal(ENTERO);
                        fila++;
                        columna = 0;
                    }else {
                        estadoFinal(ENTERO);
                        i--;
                        columna--;
                    }
                    break;
                case 2:
                    if (caracteres[i] == '0') {
                        cambioEstado(1, i);
                    }else if (isNumeroMayorACero(i)) {
                        cambioEstado(3, i);
                    }else if (caracteres[i]  == ' ' || caracteres[i] == '\t') {
                        estadoFinal(OP_ARITMETICO);
                    }else if (caracteres[i] == '\n') {
                        estadoFinal(OP_ARITMETICO);
                        fila++;
                        columna = 0;
                    }else {
                        estadoFinal(OP_ARITMETICO);
                        i--;
                        columna--;
                    }
                    break;
                case 3:
                    if (caracteres[i] == '.') {
                        cambioEstado(5, i);
                    }else if (isDigito(i)) {
                        cambioEstado(3, i);
                    }else if (caracteres[i]  == ' ') {
                        estadoFinal(ENTERO);
                    }else if (caracteres[i] == '\n') {
                        estadoFinal(ENTERO);
                        fila++;
                        columna = 0;
                    }else {
                        estadoFinal(ENTERO);
                        i--;
                        columna--;
                    }
                    break;
                case 4:
                    if (caracteres[i] == '.') {
                        cambioEstado(5, i);
                    }else if (isDigito(i)) {
                        cambioEstado(4, i);
                    }else if (caracteres[i]  == ' ' || caracteres[i] == '\t') {
                        estadoFinal(ENTERO);
                    }else if (caracteres[i] == '\n') {
                        estadoFinal(ENTERO);
                        fila++;
                        columna = 0;
                    }else {
                        estadoFinal(ENTERO);
                        i--;
                        columna--;
                    }
                    break;
                case 5:
                    if (isNumeroMayorACero(i)) {
                        cambioEstado(7, i);
                    }else if (caracteres[i] == '0') {
                        cambioEstado(6, i);
                    }else if (caracteres[i]  == ' ' || caracteres[i] == '\t') {
                        error();
                    }else if (caracteres[i] == '\n') {
                        error();
                        fila++;
                        columna = 0;
                    }else {
                        error();
                        i--;
                        columna--;
                    }
                    break;
                case 6:
                    if (isNumeroMayorACero(i)) {
                        cambioEstado(7, i);
                    }else if (caracteres[i] == '0') {
                        cambioEstado(8, i);
                    }else if (caracteres[i]  == ' ' || caracteres[i] == '\t') {
                        estadoFinal(DECIMAL);
                    }else if (caracteres[i] == '\n') {
                        estadoFinal(DECIMAL);
                        fila++;
                        columna = 0;
                    }else {
                        estadoFinal(DECIMAL);
                        i--;
                        columna--;
                    }
                    break;
                case 7:
                    if (isDigito(i)) {
                        cambioEstado(7, i);
                    }else if (caracteres[i]  == ' ' || caracteres[i] == '\t') {
                        estadoFinal(DECIMAL);
                    }else if (caracteres[i] == '\n') {
                        estadoFinal(DECIMAL);
                        fila++;
                        columna = 0;
                    }else {
                        estadoFinal(DECIMAL);
                        i--;
                        columna--;
                    }
                    break;
                case 8:
                    if (caracteres[i] == '0') {
                        cambioEstado(8, i);
                    }else if (isNumeroMayorACero(i)) {
                        cambioEstado(7, i);
                    }else if (caracteres[i]  == ' ' || caracteres[i] == '\t') {
                        error();
                    }else if (caracteres[i] == '\n') {
                        error();
                        fila++;
                        columna = 0;
                    }
                    break;
                case 9:
                    if (caracteres[i] != '"' && caracteres[i] != '\n') {
                        cambioEstado(10, i);
                    }else if (caracteres[i] == '\n') {
                        error();
                        fila++;
                        columna = 0;
                    }else if (caracteres[i] == '"') {
                        cambioEstado(11, i);
                    }
                    break;
                case 10:
                    if (caracteres[i] != '"' && caracteres[i] != '\n') {
                        cambioEstado(10, i);
                    }else if (caracteres[i] == '"') {
                        cambioEstado(11, i);
                    }else if (caracteres[i] == '\n') {
                        error();
                        fila++;
                        columna = 0;
                    }
                    break;
                case 11:
                    if (caracteres[i]  == ' ' || caracteres[i] == '\t') {
                        estadoFinal(LITERAL);
                    }else if (caracteres[i] == '\n') {
                        estadoFinal(LITERAL);
                        fila++;
                        columna = 0;
                    }else {
                        estadoFinal(LITERAL);
                        i--;
                        columna--;
                    } 
                    break;
                case 12:
                    if (caracteres[i]  == ' ' || caracteres[i] == '\t') {
                        estadoFinal(SIG_PUNTUACION);
                    }else if (caracteres[i] == '\n') {
                        estadoFinal(SIG_PUNTUACION);
                        fila++;
                        columna = 0;
                    }else {
                        estadoFinal(SIG_PUNTUACION);
                        i--;
                        columna--;
                    }
                    break;
                case 13:
                    if (caracteres[i]  == ' ' || caracteres[i] == '\t') {
                        estadoFinal(SIG_AGRUPACION);
                    }else if (caracteres[i] == '\n') {
                        estadoFinal(SIG_AGRUPACION);
                        fila++;
                        columna = 0;
                    }else {
                        estadoFinal(SIG_AGRUPACION);
                        i--;
                        columna--;
                    }
                    break;
                case 14:
                    if (caracteres[i]  == ' ' || caracteres[i] == '\t') {
                        estadoFinal(OP_ARITMETICO);
                    }else if (caracteres[i] == '\n') {
                        estadoFinal(OP_ARITMETICO);
                        fila++;
                        columna = 0;
                    }else if (caracteres[i] =='/') {
                        cambioEstado(15, i);
                    }else if (caracteres[i] =='*') {
                        cambioEstado(18, i);
                    }else {
                        estadoFinal(OP_ARITMETICO);
                        i--;
                        columna--;
                    }
                    break;
                case 15:
                    if (caracteres[i] != '\n') {
                        cambioEstado(16, i);
                    }else if (caracteres[i] == '\n') {
                        cambioEstado(17, i);
                        fila++;
                        columna = 0;
                    }
                    break;
                case 16:
                    if (caracteres[i] != '\n') {
                        cambioEstado(16, i);
                    }else if (caracteres[i] == '\n') {
                        cambioEstado(17, i);
                        fila++;
                        columna = 0;
                    }
                    break;
                case 17:
                    if (caracteres[i]  == ' ' || caracteres[i] == '\t') {
                        estadoFinal(COMENTARIO1);
                    }else if (caracteres[i] == '\n') {
                        estadoFinal(COMENTARIO1);
                        fila++;
                        columna = 0;
                    }else {
                        estadoFinal(COMENTARIO1);
                        i--;
                        columna--;
                    } 
                    break;
                case 18:
                    if (caracteres[i] != '*') {
                        cambioEstado(19, i);
                    }else if (caracteres[i] == '*') {
                        cambioEstado(20, i);
                    }
                    break;
                case 19:
                    if (caracteres[i] != '*' && caracteres[i] != '\n') {
                        cambioEstado(19, i);
                    }else if (caracteres[i] == '*') {
                        cambioEstado(20, i);
                    }else if (caracteres[i] == '\n') {
                        lexema += caracteres[i];
                        fila++;
                        columna = 0;
                    }
                    break;
                case 20:
                    if (caracteres[i] != '/' && caracteres[i] != '\n' && caracteres[i] != '*') {
                        cambioEstado(19, i);
                    }else if (caracteres[i]  == '/') {
                        cambioEstado(21, i);
                    }else if (caracteres[i] == '*') {
                        cambioEstado(20, i);
                    }else if (caracteres[i] == '\n') {
                        lexema += caracteres[i];
                        fila++;
                        columna = 0;
                    }
                    break;
                case 21:
                    if (caracteres[i]  == ' ' || caracteres[i] == '\t') {
                        estadoFinal(COMENTARIO2);
                    }else if (caracteres[i] == '\n') {
                        estadoFinal(COMENTARIO2);
                        fila++;
                        columna = 0;
                    }else {
                        estadoFinal(COMENTARIO2);
                        i--;
                        columna--;
                    } 
                    break;
                case 22:
                    if (isIdentificador(i)) {
                        cambioEstado(23, i);
                    }else if (caracteres[i]  == ' ' || caracteres[i] == '\t') {
                        error();
                    }else if (caracteres[i] == '\n') {
                        error();
                        fila++;
                        columna = 0;
                    }else {
                        error();
                        i--;
                        columna--;
                    }
                    break;
                case 23:
                    if (isIdentificador(i)) {
                        cambioEstado(23, i);
                    }else if (caracteres[i]  == ' ' || caracteres[i] == '\t') {
                        estadoFinal(IDENTIFICADOR);
                    }else if (caracteres[i] == '\n') {
                        estadoFinal(IDENTIFICADOR);
                        fila++;
                        columna = 0;
                    }else {
                        estadoFinal(IDENTIFICADOR);
                        i--;
                        columna--;
                    }
                    break;
                case 24:
                    if (isIdentificador(i) && caracteres[i] != 'o') {
                        cambioEstado(23, i);
                    }else if (caracteres[i] == 'o') {
                        cambioEstado(25, i);
                    }else if (caracteres[i]  == ' ' || caracteres[i] == '\t') {
                        error();
                    }else if (caracteres[i] == '\n') {
                        error();
                        fila++;
                        columna = 0;
                    }else {
                        error();
                        i--;
                        columna--;
                    }
                    break;
                case 25:
                    i = transicionUnEstado('o', 26, i);
                    break;
                case 26:
                    i = transicionUnEstado('l', 27, i);
                    break;
                case 27:
                    i = transicionUnEstado('e', 28, i);
                    break;
                case 28:
                    i = transicionUnEstado('a', 29, i);
                    break;
                case 29:
                    i = transicionUnEstado('n', 30, i);
                    break;
                case 30:
                    i = estadoExtremo(i);
                    break;
                case 31:
                    i = transDobleConPosibleError('l', 'a', 32, 33, i);
                    break;
                case 32:
                    i = transicionUnEstado('a', 33, i);
                    break;
                case 33:
                    i = transicionUnEstado('s', 34, i);
                    break;
                case 34:
                    if (caracteres[i] != 's'&& caracteres[i] != 'e' && isIdentificador(i)) {
                        cambioEstado(23, i);
                    }else if (caracteres[i] == 's') {
                        cambioEstado(35, i);
                    }else if (caracteres[i] == 'e') {
                        cambioEstado(36, i);
                    }else if (caracteres[i]  == ' ' || caracteres[i] == '\t') {
                        estadoFinal(IDENTIFICADOR);
                    }else if (caracteres[i] == '\n') {
                        estadoFinal(IDENTIFICADOR);
                        fila++;
                        columna = 0;
                    }else {
                        estadoFinal(IDENTIFICADOR);
                        i--;
                        columna--;
                    }
                    break;
                case 35:
                    i = estadoExtremo(i);
                    break;
                case 36:
                    i = estadoExtremo(i);
                    break;
                case 37:
                    i = transDobleConPosibleError('x', 'l', 38, 44, i);
                    break;
                case 38:
                    i = transicionUnEstado('t', 39, i);
                    break;
                case 39:
                    i = transicionUnEstado('e', 40, i);
                    break;
                case 40:
                    i = transicionUnEstado('n', 41, i);
                    break;
                case 41:
                    i = transicionUnEstado('d', 42, i);
                    break;
                case 42:
                    i = transicionUnEstado('s', 43, i);
                    break;
                case 43:
                    i = estadoExtremo(i);
                    break;
                case 44:
                    i = transicionUnEstado('s', 45, i);
                    break;
                case 45:
                    i = transicionUnEstado('e', 46, i);
                    break;
                case 46:
                    i = estadoExtremo(i);
                    break;
                case 47:
                    if (isIdentificador(i) && caracteres[i] != 'a' && caracteres[i] != 'i'
                     && caracteres[i] != 'l' && caracteres[i] != 'o') {
                        cambioEstado(23, i);
                    }else if (caracteres[i] == 'a') {
                        cambioEstado(48, i);
                    }else if (caracteres[i] == 'i') {
                        cambioEstado(52, i);
                    }else if (caracteres[i] == 'l') {
                        cambioEstado(56, i);
                    }else if (caracteres[i] == 'o') {
                        cambioEstado(60, i);
                    }else if (caracteres[i]  == ' ' || caracteres[i] == '\t') {
                        error();
                    }else if (caracteres[i] == '\n') {
                        error();
                        fila++;
                        columna = 0;
                    }else {
                        error();
                        i--;
                        columna--;
                    }
                    break;
                case 48:
                    i = transicionUnEstado('l', 49, i);
                    break;
                case 49:
                    i = transicionUnEstado('s', 50, i);
                    break;
                case 50:
                    i = transicionUnEstado('e', 51, i);
                    break;
                case 51:
                    i = estadoExtremo(i);
                    break;
                case 52:
                    i = transicionUnEstado('n', 53, i);
                    break;
                case 53:
                    i = transicionUnEstado('a', 54, i);
                    break;
                case 54:
                    i = transicionUnEstado('l', 55, i);
                    break;
                case 55:
                    i = estadoExtremo(i);
                    break;
                case 56:
                    i = transicionUnEstado('o', 57, i);
                    break;
                case 57:
                    i = transicionUnEstado('a', 58, i);
                    break;
                case 58:
                    i = transicionUnEstado('t', 59, i);
                    break;
                case 59:
                    i = estadoExtremo(i);
                    break;
                case 60:
                    i = transicionUnEstado('r', 61, i);
                    break;
                case 61:
                    i = estadoExtremo(i);
                    break;
                case 62:
                    i = transTripleConPosibleError('f', 'm', 'n', 63, 64, 76, i);
                    break;
                case 63:
                    i = estadoExtremo(i);
                    break;
                case 64:
                    i = transicionUnEstado('p', 65, i);
                    break;
                case 65:
                    if (caracteres[i] != 'l'&& caracteres[i] != 'o' && isIdentificador(i)) {
                        cambioEstado(23, i);
                    }else if (caracteres[i] == 'l') {
                        cambioEstado(66, i);
                    }else if (caracteres[i] == 'o') {
                        cambioEstado(73, i);
                    }else if (caracteres[i]  == ' ' || caracteres[i] == '\t') {
                        estadoFinal(IDENTIFICADOR);
                    }else if (caracteres[i] == '\n') {
                        estadoFinal(IDENTIFICADOR);
                        fila++;
                        columna = 0;
                    }else {
                        estadoFinal(IDENTIFICADOR);
                        i--;
                        columna--;
                    }
                    break;
                case 66:
                    i = transicionUnEstado('e', 67, i);
                    break;
                case 67:
                    i = transicionUnEstado('m', 68, i);
                    break;
                case 68:
                    i = transicionUnEstado('e', 69, i);
                    break;
                case 69:
                    i = transicionUnEstado('n', 70, i);
                    break;
                case 70:
                    i = transicionUnEstado('t', 71, i);
                    break;
                case 71:
                    i = transicionUnEstado('s', 72, i);
                    break;
                case 72:
                    i = estadoExtremo(i);
                    break;
                case 73:
                    i = transicionUnEstado('r', 74, i);
                    break;
                case 74:
                    i = transicionUnEstado('t', 75, i);
                    break;
                case 75:
                    i = estadoExtremo(i);
                    break;
                case 76:
                    i = transicionUnEstado('t', 77, i);
                    break;
                case 77:
                    if (caracteres[i] != 'e' && isIdentificador(i)) {
                        cambioEstado(23, i);
                    }else if (caracteres[i] == 'e') {
                        cambioEstado(78, i);
                    }else if (caracteres[i]  == ' ' || caracteres[i] == '\t') {
                        estadoFinal(WORD_RESERVADA);
                    }else if (caracteres[i] == '\n') {
                        estadoFinal(WORD_RESERVADA);
                        fila++;
                        columna = 0;
                    }else {
                        estadoFinal(IDENTIFICADOR);
                        i--;
                        columna--;
                    }
                    break;
                case 78:
                    i = transicionUnEstado('r', 79, i);
                    break;
                case 79:
                    i = transicionUnEstado('f', 80, i);
                    break;
                case 80:
                    i = transicionUnEstado('a', 81, i);
                    break;
                case 81:
                    i = transicionUnEstado('c', 82, i);
                    break;
                case 82:
                    i = transicionUnEstado('e', 83, i);
                    break;
                case 83:
                    i= estadoExtremo(i);
                    break;
                case 84:
                    i = transTripleConPosibleError('a', 'r', 'u', 85, 91, 104, i);
                    break;
                case 85:
                    i = transicionUnEstado('c', 86, i);
                    break;
                case 86:
                    i = transicionUnEstado('k', 87, i);
                    break;
                case 87:
                    i = transicionUnEstado('a', 88, i);
                    break;
                case 88:
                    i = transicionUnEstado('g', 89, i);
                    break;
                case 89:
                    i = transicionUnEstado('e', 90, i);
                    break;
                case 90:
                    i = estadoExtremo(i);
                    break;
                case 91:
                    if (caracteres[i] != 'i'&& caracteres[i] != 'o' && isIdentificador(i)) {
                        cambioEstado(23, i);
                    }else if (caracteres[i] == 'i') {
                        cambioEstado(92, i);
                    }else if (caracteres[i] == 'o') {
                        cambioEstado(97, i);
                    }else if (caracteres[i]  == ' ' || caracteres[i] == '\t') {
                        estadoFinal(IDENTIFICADOR);
                    }else if (caracteres[i] == '\n') {
                        estadoFinal(IDENTIFICADOR);
                        fila++;
                        columna = 0;
                    }else {
                        estadoFinal(IDENTIFICADOR);
                        i--;
                        columna--;
                    }
                    break;
                case 92:
                    i = transicionUnEstado('v', 93, i);
                    break;
                case 93:
                    i = transicionUnEstado('a', 94, i);
                    break;
                case 94:
                    i = transicionUnEstado('t', 95, i);
                    break;
                case 95:
                    i = transicionUnEstado('e', 96, i);
                    break;
                case 96:
                    i = estadoExtremo(i);
                    break;
                case 97:
                    i = transicionUnEstado('t', 98, i);
                    break;
                case 98:
                    i = transicionUnEstado('e', 99, i);
                    break;
                case 99:
                    i = transicionUnEstado('c', 100, i);
                    break;
                case 100:
                    i = transicionUnEstado('t', 101, i);
                    break;
                case 101:
                    i = transicionUnEstado('e', 102, i);
                    break;
                case 102:
                    i = transicionUnEstado('d', 103, i);
                    break;
                case 103:
                    i = estadoExtremo(i);
                    break;
                case 104:
                    i = transicionUnEstado('b', 105, i);
                    break;
                case 105:
                    i = transicionUnEstado('l', 106, i);
                    break;
                case 106:
                    i = transicionUnEstado('i', 107, i);
                    break;
                case 107:
                    i = transicionUnEstado('c', 108, i);
                    break;
                case 108:
                    i = estadoExtremo(i);
                    break;
                case 109:
                    if (isIdentificador(i) && caracteres[i] != 'e') {
                        cambioEstado(23, i);
                    }else if (caracteres[i] == 'e') {
                        cambioEstado(110, i);
                    }else if (caracteres[i]  == ' ' || caracteres[i] == '\t') {
                        error();
                    }else if (caracteres[i] == '\n') {
                        error();
                        fila++;
                        columna = 0;
                    }else {
                        error();
                        i--;
                        columna--;
                    }
                    break;
                case 110:
                    i = transicionUnEstado('t', 111, i);
                    break;
                case 111:
                    i = transicionUnEstado('u', 112, i);
                    break;
                case 112:
                    i = transicionUnEstado('r', 113, i);
                    break;
                case 113:
                    i = transicionUnEstado('n', 114, i);
                    break;
                case 114:
                    i = estadoExtremo(i);
                    break;
                case 115:
                    transDobleConPosibleError('h', 't', 116, 120, i);
                    break;
                case 116:
                    i = transicionUnEstado('o', 117, i);
                    break;
                case 117:
                    i = transicionUnEstado('r', 118, i);
                    break;
                case 118:
                    i = transicionUnEstado('t', 119, i);
                    break;
                case 119:
                    i = estadoExtremo(i);
                    break;
                case 120:
                    i = transicionUnEstado('a', 121, i);
                    break;
                case 121:
                    i = transicionUnEstado('t', 122, i);
                    break;
                case 122:
                    i = transicionUnEstado('i', 123, i);
                    break;
                case 123:
                    i = transicionUnEstado('c', 124, i);
                    break;
                case 124:
                    i = estadoExtremo(i);
                    break;
                case 125:
                    i = transDobleConPosibleError('h', 'r', 126, 129, i);
                    break;
                case 126:
                    i = transicionUnEstado('e', 127, i);
                    break;
                case 127:
                    i = transicionUnEstado('n', 128, i);
                    break;
                case 128:
                    i = estadoExtremo(i);
                    break;
                case 129:
                    i = transicionUnEstado('u', 130, i);
                    break;
                case 130:
                    i = transicionUnEstado('e', 131, i);
                    break;
                case 131:
                    i = estadoExtremo(i);
                    break;
                case 132:
                    if (isIdentificador(i) && caracteres[i] != 'o') {
                        cambioEstado(23, i);
                    }else if (caracteres[i] == 'o') {
                        cambioEstado(133, i);
                    }else if (caracteres[i]  == ' ' || caracteres[i] == '\t') {
                        error();
                    }else if (caracteres[i] == '\n') {
                        error();
                        fila++;
                        columna = 0;
                    }else {
                        error();
                        i--;
                        columna--;
                    }
                    break;
                case 133:
                    i = transicionUnEstado('i', 134, i);
                    break;
                case 134:
                    i = transicionUnEstado('d', 135, i);
                    break;
                case 135:
                    i = estadoExtremo(i);
                    break;
                case 136:
                    if (isIdentificador(i) && caracteres[i] != 'h') {
                        cambioEstado(23, i);
                    }else if (caracteres[i] == 'h') {
                        cambioEstado(137, i);
                    }else if (caracteres[i]  == ' ' || caracteres[i] == '\t') {
                        error();
                    }else if (caracteres[i] == '\n') {
                        error();
                        fila++;
                        columna = 0;
                    }else {
                        error();
                        i--;
                        columna--;
                    }
                    break;
                case 137:
                    i = transicionUnEstado('i', 138, i);
                    break;
                case 138:
                    i = transicionUnEstado('l', 139, i);
                    break;
                case 139:
                    i = transicionUnEstado('e', 140, i);
                    break;
                case 140:
                    i = estadoExtremo(i);
                    break;
            }
        }
//        for (TokenValido t : tokensValidos) {
//            System.out.println("Token: " + t.getNombreToken() + " Lexema: " + t.getLexema() + " Posicion: " + t.getPosicion());
//        }
//        
//        for (Error e : errores) {
//            System.out.println("Error - " + " Lexema: " + e.getLexema() + " Posicion: " + e.getPosicion());
//        }
    }

    public ArrayList<TokenValido> getTokensValidos() {
        return tokensValidos;
    }

    public ArrayList<Error> getErrores() {
        return errores;
    }
    
    private void cambioEstado(int newEstado, int index){
        estadoActual = newEstado;
        lexema += caracteres[index];
        columna++; 
    }
    
    private boolean isNumeroMayorACero(int index){
        boolean igualdad = false;
        for (int i = 1; i < DIGITOS.length; i++) {
            if (caracteres[index] == DIGITOS[i]) {
                igualdad =true;
            }
        }
        return igualdad;
    }
    
    private boolean isDigito(int index){
        boolean igualdad = false;
        for (int i = 0; i < DIGITOS.length; i++) {
            if (caracteres[index] == DIGITOS[i]) {
                igualdad =true;
            }
        }
        return igualdad;
    }
    
    private boolean isSigPuntuacion(int index){
        boolean igualdad = false;
        for (int i = 0; i < SIGNOS_PUNTUACION.length; i++) {
            if (caracteres[index] == SIGNOS_PUNTUACION[i]) {
                igualdad =true;
            }
        }
        return igualdad;
    }
    
    private boolean isSigAgrupacion(int index){
        boolean igualdad = false;
        for (int i = 0; i < SIGNOS_AGRUPACION.length; i++) {
            if (caracteres[index] == SIGNOS_AGRUPACION[i]) {
                igualdad =true;
            }
        }
        return igualdad;
    }
    
    private boolean isOfAbecedario(int index){
        boolean igualdad = false;
        for (int i = 0; i < ABECEDARIO.length; i++) {
            if (caracteres[index] == ABECEDARIO[i]) {
                igualdad = true;
            }
        }
        return igualdad;
    }
    
    private boolean isIdentificador(int index){
        boolean igualdad = false;
        for (int i = 0; i < ABECEDARIO.length; i++) {
            if (caracteres[index] == ABECEDARIO[i]) {
                igualdad = true;
            }
        }
        for (int i = 0; i < RESTANTES.length; i++) {
            if (caracteres[index] == RESTANTES[i]) {
                igualdad = true;
            }
        }
        for (int i = 0; i < DIGITOS.length; i++) {
            if (caracteres[index] == DIGITOS[i]) {
                igualdad = true;
            }
        }
        if (caracteres[index] == '_' || caracteres[index] == '-') {
            igualdad = true;
        }
        return igualdad;
    }
    
    private int transicionUnEstado(char exclusion, int nextEstado, int index){
        if (caracteres[index] != exclusion && isIdentificador(index)) {
            cambioEstado(23, index);
        }else if (caracteres[index] == exclusion) {
            cambioEstado(nextEstado, index);
        }else if (caracteres[index]  == ' ' || caracteres[index] == '\t') {
            estadoFinal(IDENTIFICADOR);
        }else if (caracteres[index] == '\n') {
            estadoFinal(IDENTIFICADOR);
            fila++;
            columna = 0;
        }else {
            estadoFinal(IDENTIFICADOR);
            index--;
            columna--;
        }
        return index;
    }
    
    private int transDobleConPosibleError(char c1, char c2, int e1, int e2, int index){
        if (isIdentificador(index) && caracteres[index] != c1 && caracteres[index] != c2) {
            cambioEstado(23, index);
        }else if (caracteres[index] == c1) {
            cambioEstado(e1, index);
        }else if (caracteres[index] == c2) {
            cambioEstado(e2, index);
        }else if (caracteres[index]  == ' ' || caracteres[index] == '\t') {
            error();
        }else if (caracteres[index] == '\n') {
            error();
            fila++;
            columna = 0;
        }else {
            error();
            index--;
            columna--;
        }
        return index;
    }
    
    private int transTripleConPosibleError(char c1, char c2, char c3, int e1, int e2, int e3, int index){
        if (isIdentificador(index) && caracteres[index] != c1 && caracteres[index] != c2 && caracteres[index] != c3) {
            cambioEstado(23, index);
        }else if (caracteres[index] == c1) {
            cambioEstado(e1, index);
        }else if (caracteres[index] == c2) {
            cambioEstado(e2, index);
        }else if (caracteres[index] == c3) {
            cambioEstado(e3, index);
        }else if (caracteres[index]  == ' ' || caracteres[index] == '\t') {
            error();
        }else if (caracteres[index] == '\n') {
            error();
            fila++;
            columna = 0;
        }else {
            error();
            index--;
            columna--;
        }
        return index;
    }
    
    private int estadoExtremo(int index){
        if (isIdentificador(index)) {
            cambioEstado(23, index);
        }else if (caracteres[index]  == ' ' || caracteres[index] == '\t') {
            estadoFinal(WORD_RESERVADA);
        }else if (caracteres[index] == '\n') {
            estadoFinal(WORD_RESERVADA);
            fila++;
            columna = 0;
        }else {
            estadoFinal(WORD_RESERVADA);
            index--;
            columna--;
        }
        return index;
    }
    
    private void estadoFinal(String string){
        tokensValidos.add(new TokenValido(string, lexema, posicion));
        estadoActual = 0;
        lexema = "";
        columna++;
    }
    
    private void error(){
        errores.add(new Error(lexema, posicion));
        estadoActual = 0;
        lexema = "";
        columna++;
    }
    
    
}
