package lexicalanalyzer.backend.lexemas;

public class Error {
    private String lexema;
    private String posicion;

    public Error(String lexema, String posicion) {
        this.lexema = lexema;
        this.posicion = posicion;
    }
}
