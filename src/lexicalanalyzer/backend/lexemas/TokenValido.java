package lexicalanalyzer.backend.lexemas;

public class TokenValido {
    private String nombreToken;
    private String lexema;
    private String posicion;

    public TokenValido(String nombreToken, String lexema, String posicion) {
        this.nombreToken = nombreToken;
        this.lexema = lexema;
        this.posicion = posicion;
    }

    public String getNombreToken() {
        return nombreToken;
    }

    public String getLexema() {
        return lexema;
    }

    public String getPosicion() {
        return posicion;
    }
}
