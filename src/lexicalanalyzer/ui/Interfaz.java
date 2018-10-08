/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexicalanalyzer.ui;
import java.awt.Color;
import java.awt.Event;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static javafx.scene.paint.Color.color;
import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import lexicalanalyzer.backend.Control;
import lexicalanalyzer.backend.archivos.ControladorArchivo;
import lexicalanalyzer.backend.lexemas.TokenValido;
import lexicalanalyzer.backend.lexemas.Error;

/**
 *
 * @author asael
 */
public class Interfaz extends javax.swing.JFrame {

    private String path = "";
    private final ControladorArchivo file = new ControladorArchivo();
    private static boolean notChanges = true;
    private Control control;
    
    /**
     * Creates new form Interfaz
     */
    public Interfaz() {
        validarCierre();
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        reporteToken = new javax.swing.JDialog();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableTokens = new javax.swing.JTable();
        reporteErrores = new javax.swing.JDialog();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableErrores = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        areaTexto = new javax.swing.JTextArea();
        cordCursor = new javax.swing.JLabel();
        btnAnalizar = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        txtBusqueda = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuArchivo = new javax.swing.JMenu();
        itemAbrir = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        itemNuevo = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        itemGuardar = new javax.swing.JMenuItem();
        itemGuardarComo = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        menuEditar = new javax.swing.JMenu();
        itemDeshacer = new javax.swing.JMenuItem();
        itemRehacer = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        itemCortar = new javax.swing.JMenuItem(new DefaultEditorKit.CutAction());
        itemCopiar = new javax.swing.JMenuItem(new DefaultEditorKit.CopyAction());
        itemPegar = new javax.swing.JMenuItem(new DefaultEditorKit.PasteAction());
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        itemBuscar = new javax.swing.JMenuItem();
        menuAyuda = new javax.swing.JMenu();
        itemAyuda = new javax.swing.JMenuItem();
        itemAcerca = new javax.swing.JMenuItem();

        reporteToken.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        reporteToken.setTitle("Reporte Tokens Validos");
        reporteToken.setSize(new java.awt.Dimension(632, 370));

        tableTokens.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre del Token", "Lexema", "Posicion"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableTokens);

        javax.swing.GroupLayout reporteTokenLayout = new javax.swing.GroupLayout(reporteToken.getContentPane());
        reporteToken.getContentPane().setLayout(reporteTokenLayout);
        reporteTokenLayout.setHorizontalGroup(
            reporteTokenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reporteTokenLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        reporteTokenLayout.setVerticalGroup(
            reporteTokenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reporteTokenLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(55, Short.MAX_VALUE))
        );

        reporteErrores.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        reporteErrores.setPreferredSize(new java.awt.Dimension(632, 370));
        reporteErrores.setSize(new java.awt.Dimension(632, 370));

        tableErrores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Lexema", "Posicion"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tableErrores);

        javax.swing.GroupLayout reporteErroresLayout = new javax.swing.GroupLayout(reporteErrores.getContentPane());
        reporteErrores.getContentPane().setLayout(reporteErroresLayout);
        reporteErroresLayout.setHorizontalGroup(
            reporteErroresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reporteErroresLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        reporteErroresLayout.setVerticalGroup(
            reporteErroresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reporteErroresLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(58, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setSize(new java.awt.Dimension(600, 400));

        areaTexto.setBackground(new java.awt.Color(14, 23, 39));
        areaTexto.setColumns(20);
        areaTexto.setForeground(new java.awt.Color(204, 204, 204));
        areaTexto.setRows(5);
        areaTexto.setCaretColor(new java.awt.Color(255, 255, 255));
        UndoManager undoManager = new UndoManager();
        Document doc = areaTexto.getDocument();

        doc.addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
                undoManager.addEdit(e.getEdit());
            }
        });
        areaTexto.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                areaTextoCaretUpdate(evt);
            }
        });
        KeyStroke undoKeyStroke = KeyStroke.getKeyStroke(
            KeyEvent.VK_Z, Event.CTRL_MASK);
        KeyStroke redoKeyStroke = KeyStroke.getKeyStroke(
            KeyEvent.VK_Y, Event.CTRL_MASK);

        // Map undo action
        areaTexto.getInputMap(areaTexto.WHEN_IN_FOCUSED_WINDOW)
        .put(undoKeyStroke, "undoKeyStroke");
        areaTexto.getActionMap().put("undoKeyStroke", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    undoManager.undo();
                } catch (CannotUndoException cue) {}
            }
        });
        // Map redo action
        areaTexto.getInputMap(areaTexto.WHEN_IN_FOCUSED_WINDOW)
        .put(redoKeyStroke, "redoKeyStroke");
        areaTexto.getActionMap().put("redoKeyStroke", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    undoManager.redo();
                } catch (CannotRedoException cre) {}
            }
        });

        areaTexto.getDocument().addDocumentListener(new CambiosListener());
        jScrollPane2.setViewportView(areaTexto);

        cordCursor.setText("Linea: 1 - Columna: 1");

        btnAnalizar.setText("Analizar");
        btnAnalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnalizarActionPerformed(evt);
            }
        });

        jButton3.setText("Buscar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAnalizar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 94, Short.MAX_VALUE)
                .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3)
                .addGap(54, 54, 54)
                .addComponent(cordCursor)
                .addContainerGap())
            .addComponent(jScrollPane2)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cordCursor)
                    .addComponent(btnAnalizar)
                    .addComponent(jButton3)
                    .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        menuArchivo.setText("Archivo");

        itemAbrir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        itemAbrir.setText("Abrir");
        itemAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemAbrirActionPerformed(evt);
            }
        });
        menuArchivo.add(itemAbrir);
        menuArchivo.add(jSeparator1);

        itemNuevo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        itemNuevo.setText("Nuevo");
        itemNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemNuevoActionPerformed(evt);
            }
        });
        menuArchivo.add(itemNuevo);
        menuArchivo.add(jSeparator2);

        itemGuardar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        itemGuardar.setText("Guardar");
        itemGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemGuardarActionPerformed(evt);
            }
        });
        menuArchivo.add(itemGuardar);

        itemGuardarComo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        itemGuardarComo.setText("Guardar Como");
        itemGuardarComo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemGuardarComoActionPerformed(evt);
            }
        });
        menuArchivo.add(itemGuardarComo);

        jMenuItem1.setText("Salir");
        menuArchivo.add(jMenuItem1);

        jMenuBar1.add(menuArchivo);

        menuEditar.setText("Editar");

        itemDeshacer.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        itemDeshacer.setText("Deshacer");
        itemDeshacer.addActionListener((ActionEvent e) -> {
            try {
                undoManager.undo();
            } catch (CannotUndoException cue) {}
        });
        menuEditar.add(itemDeshacer);

        itemRehacer.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        itemRehacer.setText("Rehacer");
        itemRehacer.addActionListener((ActionEvent e) -> {
            try {
                undoManager.redo();
            } catch (CannotRedoException cre) {}
        });
        menuEditar.add(itemRehacer);
        menuEditar.add(jSeparator3);

        itemCortar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        itemCortar.setText("Cortar");
        menuEditar.add(itemCortar);

        itemCopiar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        itemCopiar.setText("Copiar");
        menuEditar.add(itemCopiar);

        itemPegar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        itemPegar.setText("Pegar");
        menuEditar.add(itemPegar);
        menuEditar.add(jSeparator4);

        itemBuscar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.CTRL_MASK));
        itemBuscar.setText("Buscar");
        menuEditar.add(itemBuscar);

        jMenuBar1.add(menuEditar);

        menuAyuda.setText("Ayuda");

        itemAyuda.setText("Ayuda");
        menuAyuda.add(itemAyuda);

        itemAcerca.setText("Acerca de");
        menuAyuda.add(itemAcerca);

        jMenuBar1.add(menuAyuda);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void areaTextoCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_areaTextoCaretUpdate
        javax.swing.JTextArea editArea = (javax.swing.JTextArea)evt.getSource();
        int linea = 1;
        int columna = 1;
        
        try {
            int caretPos = editArea.getCaretPosition();
            linea = editArea.getLineOfOffset(caretPos);
            columna = caretPos - editArea.getLineStartOffset(linea);
            
            linea += 1;
            columna += 1;
        } catch (Exception e) {
        }
        cordCursor.setText("Linea: " + linea + " - Columna: " + columna);
    }//GEN-LAST:event_areaTextoCaretUpdate

    private void itemAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemAbrirActionPerformed
        if (notChanges) {
            notChanges = false;
            JFileChooser fc = new JFileChooser();
            fc.showOpenDialog(this);
            try {
                path = fc.getSelectedFile().getAbsolutePath();
                notChanges = true;   
                areaTexto.setText(file.readFile(path));
            } catch (Exception e) {
                System.out.println("se cancelo");
            }
        }else {
            cambiosSinGuardar(evt, 1);
        }
    }//GEN-LAST:event_itemAbrirActionPerformed

    private void itemNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemNuevoActionPerformed
        if (notChanges) {
            areaTexto.setText("");
            path = "";
            notChanges = true;
        }else {
            cambiosSinGuardar(evt, 2);
        }
    }//GEN-LAST:event_itemNuevoActionPerformed

    private void itemGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemGuardarActionPerformed
        String texto = areaTexto.getText();
        if (file.verifyFile(path)) {
            System.out.println(texto);
            file.saveFile(path, texto);
            notChanges = true;
        }else {
            JFileChooser fc= new JFileChooser(); 
            path = ""; 
            try{
                if(fc.showSaveDialog(null)==fc.APPROVE_OPTION){ 
                    try {
                        path = fc.getSelectedFile().getAbsolutePath() + ".txt";    
                    notChanges = true;
                    } catch (Exception e) {
                        System.out.println("se cancelo");
                    }
                }
                System.out.println(texto);
                file.saveFile(path, texto);
            }catch (HeadlessException ex){ 
                //ex.printStackTrace();
            } 
        }
    }//GEN-LAST:event_itemGuardarActionPerformed

    private void itemGuardarComoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemGuardarComoActionPerformed
        String texto = areaTexto.getText();
        JFileChooser fc= new JFileChooser(); 
            path = ""; 
            try{
                if(fc.showSaveDialog(null)==fc.APPROVE_OPTION){ 
                    try {
                            path = fc.getSelectedFile().getAbsolutePath() + ".txt";    
                    } catch (Exception e) {
                        System.out.println("se cancelo");
                    }
                }
                System.out.println(texto);
                file.saveFile(path, texto);
                notChanges = true;
            }catch (HeadlessException ex){ 
                //ex.printStackTrace();
            }
    }//GEN-LAST:event_itemGuardarComoActionPerformed

    private void btnAnalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnalizarActionPerformed
        String texto = areaTexto.getText() + " ";
        char[] caracteres = texto.toCharArray();
        control = new Control(caracteres);
        if (!control.getErrores().isEmpty()) {
            tablaReporteErrores();
            abrirDialog(reporteErrores);
        }else {
            tablaReporteTokens();
            abrirDialog(reporteToken);
        }
        
    }//GEN-LAST:event_btnAnalizarActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        buscarpalabra(areaTexto, txtBusqueda.getText());
    }//GEN-LAST:event_jButton3ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea areaTexto;
    private javax.swing.JButton btnAnalizar;
    private javax.swing.JLabel cordCursor;
    private javax.swing.JMenuItem itemAbrir;
    private javax.swing.JMenuItem itemAcerca;
    private javax.swing.JMenuItem itemAyuda;
    private javax.swing.JMenuItem itemBuscar;
    private javax.swing.JMenuItem itemCopiar;
    private javax.swing.JMenuItem itemCortar;
    private javax.swing.JMenuItem itemDeshacer;
    private javax.swing.JMenuItem itemGuardar;
    private javax.swing.JMenuItem itemGuardarComo;
    private javax.swing.JMenuItem itemNuevo;
    private javax.swing.JMenuItem itemPegar;
    private javax.swing.JMenuItem itemRehacer;
    private javax.swing.JButton jButton3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JMenu menuArchivo;
    private javax.swing.JMenu menuAyuda;
    private javax.swing.JMenu menuEditar;
    private javax.swing.JDialog reporteErrores;
    private javax.swing.JDialog reporteToken;
    private javax.swing.JTable tableErrores;
    private javax.swing.JTable tableTokens;
    private javax.swing.JTextField txtBusqueda;
    // End of variables declaration//GEN-END:variables

    public static void setCambio(boolean change){
        notChanges = change;
    }
    
    public void buscarpalabra(JTextArea area1, String texto) {
        if (texto.length() >= 1) {
            DefaultHighlighter.DefaultHighlightPainter highlightPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.GREEN);
            Highlighter h = area1.getHighlighter();
            h.removeAllHighlights();
            String text = area1.getText();
            String caracteres = texto;
            Pattern p = Pattern.compile("(?i)" + caracteres);
            Matcher m = p.matcher(text);
            while (m.find()) {
                try {
                    h.addHighlight(m.start(), m.end(), highlightPainter);
                } catch (BadLocationException ex) {
                    Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            JOptionPane.showMessageDialog(area1, "la palabra a buscar no puede ser vacia");
        }
    }
    
    public void cambiosSinGuardar(ActionEvent evt, int op){
        String[] options = {"Guardar Cambios", "Desechar Cambios", "Cancelar"};
        int selection = JOptionPane.showOptionDialog(null, "Hay cambios sin guardar!", 
            "Informacion", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, 
            null, options, options[0]);

        switch (selection) {
            case 0:
                itemGuardarActionPerformed(evt);
                break;
            case 1:
                switch (op) {
                    case 0:
                        System.exit(0);
                        break;
                    case 1:
                        notChanges = true;
                        itemAbrirActionPerformed(evt);
                        break;
                    case 2:
                        itemNuevoActionPerformed(evt);
                        break;
                }
                break;
            case 2:
                
                break;
        }
    }

    private void validarCierre() {
        try {
            this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e){
                    if (notChanges) {
                        System.exit(0);
                    }else {
                        cambiosSinGuardar(null, 0);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void tablaReporteTokens(){
        DefaultTableModel model = (DefaultTableModel)tableTokens.getModel();
        model.setRowCount(0);
        
        ArrayList<TokenValido> listToken = control.getTokensValidos();
        for (TokenValido t : listToken) {
            Object item[] = new Object[3];
            item[0] = t.getNombreToken();
            item[1] = t.getLexema();
            item[2] = t.getPosicion();
            model.addRow(item);
        }
        tableTokens.setModel(model);
    }
    
    private void tablaReporteErrores(){
        DefaultTableModel model = (DefaultTableModel)tableErrores.getModel();
        model.setRowCount(0);
        
        ArrayList<Error> listError = control.getErrores();
        for (Error e : listError) {
            Object item[] = new Object[2];
            item[0] = e.getLexema();
            item[1] = e.getPosicion();
            model.addRow(item);
        }
        tableErrores.setModel(model);
    }
    
    private void abrirDialog(JDialog jd){
        jd.setResizable(false);
        jd.setLocationRelativeTo(null);
        jd.setVisible(true);
    }
}
