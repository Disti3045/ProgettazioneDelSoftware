
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class InterfacciaGrafica extends JFrame {

    private Comune[] comuni;
    private Persona[] persone;

    private JTextField txtCerca;
    private JButton btnCerca;
    private JPanel panelBottoni; 
    private JTextArea txtDettagli;
    private JButton btnBack;
    private JSplitPane splitPane;

    public InterfacciaGrafica(Comune[] comuni, Persona[] persone) {
        this.comuni = comuni;
        this.persone = persone;

        setTitle("Gestione Catasto");
        setSize(800, 600);
        setResizable(false); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelNord = new JPanel();
        panelNord.add(new JLabel("Codice Catastale Comune:"));
        txtCerca = new JTextField(10); 
        btnCerca = new JButton("Cerca");
        panelNord.add(txtCerca);
        panelNord.add(btnCerca);
        
        add(panelNord, BorderLayout.NORTH);

        panelBottoni = new JPanel(new GridLayout(0, 1, 5, 5));
        
        JPanel wrapperBottoni = new JPanel(new BorderLayout());
        wrapperBottoni.add(panelBottoni, BorderLayout.NORTH);
        
        JScrollPane scrollBottoni = new JScrollPane(wrapperBottoni);
        scrollBottoni.setBorder(BorderFactory.createTitledBorder("Immobili Trovati"));
        scrollBottoni.getVerticalScrollBar().setUnitIncrement(16);

        txtDettagli = new JTextArea();
        txtDettagli.setEditable(false); 
        txtDettagli.setLineWrap(true); 
        txtDettagli.setWrapStyleWord(true);
        JScrollPane scrollDettagli = new JScrollPane(txtDettagli);
        scrollDettagli.setBorder(BorderFactory.createTitledBorder("Dettagli Immobile"));

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollBottoni, scrollDettagli);
        
        splitPane.setEnabled(false); 
        splitPane.setDividerLocation(260); 
        
        add(splitPane, BorderLayout.CENTER);

        btnBack = new JButton("Indietro");
        add(btnBack, BorderLayout.SOUTH);

        btnCerca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codiceCercato = txtCerca.getText().trim();
                cercaComuneEGeneraBottoni(codiceCercato);
            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetInterfaccia();
            }
        });
        
        setVisible(true);
    }

    private void cercaComuneEGeneraBottoni(String codice) {
        panelBottoni.removeAll(); 
        txtDettagli.setText(""); 
        
        if(codice.isEmpty()) {
             JOptionPane.showMessageDialog(this, "Inserisci un codice catastale.", "Errore", JOptionPane.WARNING_MESSAGE);
             refreshPannelloBottoni();
             return;
        }

        Comune comuneTrovato = null;
        for (Comune c : comuni) {
            if (c.GetComune().equalsIgnoreCase(codice)) {
                comuneTrovato = c;
                break;
            }
        }

        if (comuneTrovato == null || comuneTrovato.immobili == null || comuneTrovato.immobili.length == 0) {
            JOptionPane.showMessageDialog(this, "Questo comune non ha immobili registrati", "Risultato Ricerca", JOptionPane.ERROR_MESSAGE);
            refreshPannelloBottoni();
            return;
        }

        for (Immobile im : comuneTrovato.immobili) {
            JButton btnImmobile = new JButton("Imm. " + im.numero_catastale);
            btnImmobile.setPreferredSize(new Dimension(200, 40)); 
            
            btnImmobile.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mostraDettagliImmobile(im);
                }
            });

            panelBottoni.add(btnImmobile);
        }

        refreshPannelloBottoni();
    }

    private void mostraDettagliImmobile(Immobile im) {
        StringBuilder sb = new StringBuilder();
        
        sb.append("DETTAGLI IMMOBILE SELEZIONATO\n");
        sb.append("-----------------------------\n");
        sb.append("Comune:    ").append(im.comune.GetComune()).append("\n");
        sb.append("Catastale: ").append(im.numero_catastale).append("\n");
        sb.append("Rendita:   ").append(String.format("%.2f", im.rendita_catastale)).append(" €\n"); 
        sb.append("\n");
        
        sb.append("PROPRIETARI REGISTRATI\n");
        sb.append("-----------------------------\n");
        
        if(im.proprietari.length > 0) {
            for(int i = 0; i < im.proprietari.length; i++) {
                String infoPersona = Immobile.GetPersonaFromCodiceFiscale(im.proprietari[i], persone);
                
                sb.append((i+1) + ". "); 
                if(infoPersona != null) {
                    sb.append(infoPersona).append("\n   (CF: ").append(im.proprietari[i]).append(")\n");
                } else {
                    sb.append("CF: ").append(im.proprietari[i]).append("\n   [Nessuna anagrafica trovata]\n");
                }
                sb.append("\n");
            }
        } else {
            sb.append("Nessun proprietario presente nel database.\n");
        }

        txtDettagli.setText(sb.toString());
        txtDettagli.setCaretPosition(0);
    }

    private void resetInterfaccia() {
        txtCerca.setText("");
        panelBottoni.removeAll();
        txtDettagli.setText("");
        refreshPannelloBottoni();
    }
    
    private void refreshPannelloBottoni() {
        panelBottoni.revalidate();
        panelBottoni.repaint();
    }
}