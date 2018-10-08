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
    private final char[] SIG_AGRUPACION = {'(',')','{','}','[',']'};
    private final char[] SIG_PUNTUACION = {',','.',';',':'};
    
    private final String ENTERO = "Numero Entero";
    private final String OP_ARITMETICO = "Operador Aritmetico";
    private final String DECIMAL = "Decimal";
        
    private final char[] caracteres;
    private String lexema;
    private int fila;
    private int columna;
    private ArrayList<TokenValido> tokensValidos = new ArrayList();
    private ArrayList<Error> errores = new ArrayList();
    private int estadoActual;

    public Control(char[] chars) {
        this.caracteres = chars;
        estadoActual = 0;
        lexema = "";
        columna = 1;
        for (int i = 0; i < caracteres.length; i++) {
            switch (estadoActual) {
                case 0:
                    if (caracteres[i] == '0') {
                        cambioEstado(1, i);
                    }else if (caracteres[i] == '+' || caracteres[i] == '-') {
                        cambioEstado(2, i);
                    }else if (isNumeroMayorACero(i)) {
                        cambioEstado(4, i);
                    }else if (caracteres[i]  == ' ') {
                        columna++;
                    }
                    break;
                case 1:
                    if (caracteres[i] == '.') {
                        cambioEstado(5, i);
                    }else if (caracteres[i]  == ' ') {
                        estadoFinal(ENTERO);
                    }else {
                        estadoFinal(ENTERO);
                    }
                    break;
                case 2:
                    if (caracteres[i] == '0') {
                        cambioEstado(1, i);
                    }else if (isNumeroMayorACero(i)) {
                        cambioEstado(3, i);
                    }else if (caracteres[i]  == ' ') {
                        estadoFinal(OP_ARITMETICO);
                    }else {
                        estadoFinal(OP_ARITMETICO);
                        i--;
                    }
                    break;
                case 3:
                    if (caracteres[i] == '.') {
                        cambioEstado(5, i);
                    }else if (isDigito(i)) {
                        cambioEstado(3, i);
                    }else if (caracteres[i]  == ' ') {
                        estadoFinal(ENTERO);
                    }else {
                        estadoFinal(ENTERO);
                        i--;
                    }
                    break;
                case 4:
                    if (caracteres[i] == '.') {
                        cambioEstado(5, i);
                    }else if (isDigito(i)) {
                        cambioEstado(4, i);
                    }else if (caracteres[i]  == ' ') {
                        estadoFinal(ENTERO);
                    }else {
                        estadoFinal(ENTERO);
                        i--;
                    }
                    break;
                case 5:
                    if (isNumeroMayorACero(i)) {
                        cambioEstado(7, i);
                    }else if (caracteres[i] == '0') {
                        cambioEstado(6, i);
                    }else if (caracteres[i]  == ' ') {
                        columna++;
                    }
                    break;
                case 6:
                    if (isNumeroMayorACero(i)) {
                        cambioEstado(7, i);
                    }else if (caracteres[i] == '0') {
                        cambioEstado(8, i);
                    }else if (caracteres[i]  == ' ') {
                        estadoFinal(DECIMAL);
                    }else {
                        estadoFinal(DECIMAL);
                        i--;
                    }
                    break;
                case 7:
                    if (isDigito(i)) {
                        cambioEstado(7, i);
                    }else if (caracteres[i]  == ' ') {
                        estadoFinal(DECIMAL);
                    }else {
                        estadoFinal(DECIMAL);
                        i--;
                    }
                    break;
                case 8:
                    if (caracteres[i] == '0') {
                        cambioEstado(8, i);
                    }else if (isNumeroMayorACero(i)) {
                        cambioEstado(7, i);
                    }else if (caracteres[i]  == ' ') {
                        columna++;
                    }
                    break;
                case 9:
                    
                    break;
                case 10:
                    
                    break;
                case 11:
                    
                    break;
                case 12:
                    
                    break;
                case 13:
                    
                    break;
                case 14:
                    
                    break;
            }
        }
        for (TokenValido t : tokensValidos) {
            System.out.println("Token: " + t.getNombreToken() + " Lexema: " + t.getLexema() + " Posicion: " + t.getPosicion());
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
    
    private void estadoFinal(String string){
        tokensValidos.add(new TokenValido(string, lexema, "("+fila+","+columna+")"));
        estadoActual = 0;
        lexema = "";
        columna++;
    }
    
}
