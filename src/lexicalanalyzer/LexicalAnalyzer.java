package lexicalanalyzer;

import javax.swing.JFrame;
import lexicalanalyzer.ui.Interfaz;

/**
 *
 * @author asael
 */
public class LexicalAnalyzer {

    public static void main(String[] args) {
        Interfaz ui = new Interfaz();
        ui.setExtendedState(JFrame.MAXIMIZED_BOTH);
        ui.setLocationRelativeTo(null);
        ui.setTitle("Analizador Lexico");
        ui.setVisible(true);
    }
    
}
