package lexicalanalyzer.ui;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class CambiosListener implements DocumentListener{

    public CambiosListener() {
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        Interfaz.setCambio(false);
        System.out.println("Insert text");
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        Interfaz.setCambio(false);
        System.out.println("Delete text");
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        Interfaz.setCambio(false);
        System.out.println("Rare event");
    }
    
}
