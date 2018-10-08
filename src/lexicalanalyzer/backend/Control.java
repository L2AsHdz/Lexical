package lexicalanalyzer.backend;

import java.util.ArrayList;
import lexicalanalyzer.backend.lexemas.TokenValido;
import lexicalanalyzer.backend.lexemas.Error;

public class Control {
    private final char[] ABECEDARIO = {'a','b','c','d','e','f','g','h','i','j','k','l',
                            'm','n','o','p','q','r','s','t','u','v','w','x','y','z',
                            'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O',
                            'P','Q','R','S','T','U','V','W','X','Y','Z'};
    private final char[] DIGITOS = {'0','1','2','3','4','5','6','7','8','9'};
    private final char[] OP_ARITMETICOS = {'+','-','*','/','%'};
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
                    }else if (caracteres[i]  == ' ' || caracteres[i] == '\t') {
                        columna++;
                    }else if (caracteres[i] =='\n') {
                        fila++;
                        columna = 0;
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
                    if (caracteres[i] != '/' && caracteres[i] != '\n') {
                        cambioEstado(19, i);
                    }else if (caracteres[i]  == '/') {
                        cambioEstado(21, i);
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
                    break;
                case 23:
                    break;
                case 24:
                    break;
                case 25:
                    break;
            }
        }
        for (TokenValido t : tokensValidos) {
            System.out.println("Token: " + t.getNombreToken() + " Lexema: " + t.getLexema() + " Posicion: " + t.getPosicion());
        }
        
        for (Error e : errores) {
            System.out.println("Error - " + " Lexema: " + e.getLexema() + " Posicion: " + e.getPosicion());
        }
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
