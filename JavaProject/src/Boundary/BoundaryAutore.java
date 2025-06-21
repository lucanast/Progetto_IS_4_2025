package Boundary;

import Control.ControlAutore;
import Entity.Autore;
import Entity.Raccolta;
import Entity.Ricetta;
import Entity.Profilo;
import Entity.Commento;
import exception.ErroreCredenziali;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

public class BoundaryAutore extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Autore currentAutore;
    private ControlAutore control;
    private JTextField emailField;
    private JPasswordField passwordField;



    public BoundaryAutore() {
        setTitle("Autore - Area Protetta");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        control = new ControlAutore();
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createLoginPanel(), "login");
        mainPanel.add(createActionPanel(), "actions");
        add(mainPanel);

        cardLayout.show(mainPanel, "login");
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
         emailField = new JTextField();
        passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel());
        panel.add(loginButton);

        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String pwd = new String(passwordField.getPassword());
            try {
                currentAutore = control.login(email,pwd);
                JOptionPane.showMessageDialog(this, "Login riuscito!");
                cardLayout.show(mainPanel, "actions");
            } catch (ErroreCredenziali ex) {
                JOptionPane.showMessageDialog(this, "Credenziali non valide ");

            }
        });
        return panel;
    }

    private JPanel createActionPanel() {
        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));
        JButton btnCreaRic = new JButton("Crea Ricetta");
        JButton btnCreaRac = new JButton("Crea Raccolta");
        JButton btnModProf = new JButton("Modifica Profilo");
        //JButton btnAddComm = new JButton("Aggiungi Commento");
        JButton btnLogout = new JButton("Logout");
        JButton btnFeed=new JButton("Feed");
        JButton btnRicetta = new JButton("Le Mie Ricetta");

        panel.add(btnCreaRic);
        panel.add(btnCreaRac);
        panel.add(btnModProf);
        panel.add(btnRicetta);
        panel.add(btnFeed);
        panel.add(btnLogout);


        btnCreaRic.addActionListener(e -> creaRicettaDialog());
        btnCreaRac.addActionListener(e -> creaRaccoltaDialog());
        btnModProf.addActionListener(e -> modificaProfiloDialog());
        //btnAddComm.addActionListener(e -> aggiungiCommentoDialog());
        btnRicetta.addActionListener(e -> mostraMieRicette());
        btnFeed.addActionListener(e -> mostraFeedDialog());
        btnLogout.addActionListener(e -> {
            currentAutore = null;
            emailField.setText("");
            passwordField.setText("");
            cardLayout.show(mainPanel, "login");
        });


        return panel;
    }

    private void creaRicettaDialog() {
        String titolo = JOptionPane.showInputDialog(this, "Titolo ricetta:");
        if (titolo == null) return;

        String descrizione = JOptionPane.showInputDialog(this, "Descrizione:");
        if (descrizione == null) return;

        String tempoStr = JOptionPane.showInputDialog(this, "Tempo (in minuti):");
        if (tempoStr == null) return;

        String tag = JOptionPane.showInputDialog(this, "Tag (es: dolce, vegetariano):");
        if (tag == null) return;

        String titoloRaccolta = JOptionPane.showInputDialog(this, "Titolo raccolta (nuova o esistente):");
        if (titoloRaccolta == null) return;

        try {
            int tempo = Integer.parseInt(tempoStr.trim());

            Raccolta raccolta = new Raccolta();
            raccolta.setTitolo(titoloRaccolta.trim());
            raccolta.setDescrizione(descrizione.trim());
            raccolta.setAutore(currentAutore);

            Ricetta ricetta = new Ricetta();
            ricetta.setTitolo(titolo.trim());
            ricetta.setDescrizione(descrizione.trim());
            ricetta.setTempo(tempo);
            ricetta.setTag(tag.trim());
            ricetta.setAutore(currentAutore);
            ricetta.setRaccolta(raccolta);

            control.creaRicetta(ricetta);
            JOptionPane.showMessageDialog(this, "✅ Ricetta creata con successo!");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "⚠️ Inserisci un numero valido per il tempo.", "Formato non valido", JOptionPane.WARNING_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "⚠️ " + ex.getMessage(), "Dati non validi", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "❌ Errore durante la creazione:\n" + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }



    private void creaRaccoltaDialog() {
        String titolo = JOptionPane.showInputDialog(this, "Titolo raccolta:");
        if (titolo == null) return;

        String descrizione = JOptionPane.showInputDialog(this, "Descrizione:");
        if (descrizione == null) return;

        int priv = JOptionPane.showConfirmDialog(this, "Vuoi che la raccolta sia privata?", "Visibilità",
                JOptionPane.YES_NO_OPTION);
        boolean isPrivate = (priv == JOptionPane.YES_OPTION);

        // 📦 Prepara oggetto Raccolta
        Raccolta raccolta = new Raccolta();
        raccolta.setTitolo(titolo.trim());
        raccolta.setDescrizione(descrizione.trim());
        raccolta.setPrivate(isPrivate);
        raccolta.setAutore(currentAutore);

        try {
            control.creaRaccolta(raccolta);
            JOptionPane.showMessageDialog(this, "✅ Raccolta creata con successo!");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "⚠️ " + ex.getMessage(), "Raccolta non valida", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "❌ Errore durante la creazione:\n" + ex.getMessage(),
                    "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }




    private void modificaProfiloDialog() {
        String bio = JOptionPane.showInputDialog(this, "Nuova biografia:");
        if (bio == null) return;

        String img = JOptionPane.showInputDialog(this, "Nuovo URL immagine:");
        if (img == null) return;

        String dati = JOptionPane.showInputDialog(this, "Altri dati:");
        if (dati == null) return;

        Profilo p = new Profilo();
        p.setAutore(currentAutore);
        p.setBiografia(bio.trim());
        p.setImmagine(img.trim());
        p.setDati(dati.trim());

        try {
            control.modificaProfilo(p, currentAutore);
            JOptionPane.showMessageDialog(this, "✅ Profilo aggiornato con successo!");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "⚠️ " + ex.getMessage(), "Dati non validi", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "❌ Errore modifica profilo:\n" + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }





    private void mostraFeedDialog() {
        try {
            ArrayList<Ricetta> ricette = control.mostraFeedDaAltriAutori(currentAutore.getEmail());

            if (ricette.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nessuna ricetta trovata nel feed.");
                return;
            }

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            for (Ricetta r : ricette) {
                JPanel card = new JPanel(new BorderLayout());
                card.setBorder(BorderFactory.createTitledBorder(r.getTitolo()));

                JTextArea area = new JTextArea();
                area.setEditable(false);
                area.setFont(new Font("SansSerif", Font.PLAIN, 12));
                StringBuilder contenuto = new StringBuilder();
                contenuto.append("⏱ ").append(r.getTempo()).append(" min\n");
                contenuto.append("📎 Tag: ").append(r.getTag()).append("\n\n");
                contenuto.append(r.getDescrizione()).append("\n\n");
                contenuto.append("🧾 Numero commenti: ").append(r.getNumeroCommenti()).append("\n");
                contenuto.append("💬 Ultimi commenti:\n");

                ArrayList<Commento> comms = control.getCommentiPerRicetta(r.getId());
                if (comms.isEmpty()) {
                    contenuto.append("  Nessun commento ancora.\n");
                } else {
                    for (Commento c : comms) {
                        contenuto.append("  - ")
                                .append(c.getAutore().getEmail())
                                .append(": ")
                                .append(c.getTesto())
                                .append("\n");

                    }
                }

                area.setText(contenuto.toString());
                card.add(new JScrollPane(area), BorderLayout.CENTER);

                JButton btnCommenta = new JButton("Commenta");
                btnCommenta.addActionListener(e -> {
                    String testo = JOptionPane.showInputDialog(this, "Inserisci il tuo commento:");
                    if (testo != null && !testo.trim().isEmpty()) {
                        try {
                            Commento nuovoCommento = new Commento();
                            nuovoCommento.setTesto(testo);
                            nuovoCommento.setDataPubblicazione(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                            nuovoCommento.setAutore(currentAutore);  // L'autore loggato

                            nuovoCommento.setRicetta(r);             // La ricetta corrente nel ciclo

                            control.aggiungiCommento(nuovoCommento);
                            JOptionPane.showMessageDialog(this, "Commento aggiunto!");
                            JOptionPane.getRootFrame().dispose(); // 🧹 chiude la finestra corrente
                            mostraFeedDialog();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(this, "Errore durante l'aggiunta del commento:\n" + ex.getMessage());
                        }
                    }
                });


                card.add(btnCommenta, BorderLayout.SOUTH);
                panel.add(card);
                panel.add(Box.createVerticalStrut(10)); // spazio tra le ricette
            }

            JScrollPane scroll = new JScrollPane(panel);
            scroll.setPreferredSize(new Dimension(500, 400));
            JOptionPane.showMessageDialog(this, scroll, "Feed Ricette di altri Autori", JOptionPane.PLAIN_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Errore nel caricamento del feed: " + ex.getMessage(),
                    "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void mostraMieRicette() {
        try {
            ArrayList<Ricetta> mieRicette = control.getRicetteDiAutore(currentAutore.getEmail());

            if (mieRicette.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Non hai ancora creato nessuna ricetta.");
                return;
            }

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            for (Ricetta r : mieRicette) {
                JPanel card = new JPanel(new BorderLayout());
                card.setBorder(BorderFactory.createTitledBorder(r.getTitolo()));

                JTextArea area = new JTextArea();
                area.setEditable(false);
                area.setFont(new Font("SansSerif", Font.PLAIN, 12));

                StringBuilder contenuto = new StringBuilder();
                contenuto.append("⏱ Tempo: ").append(r.getTempo()).append(" min\n");
                contenuto.append("📎 Tag: ").append(r.getTag()).append("\n\n");
                contenuto.append(r.getDescrizione()).append("\n\n");
                contenuto.append("🧾 Numero commenti: ").append(r.getNumeroCommenti()).append("\n");

                area.setText(contenuto.toString());
                card.add(new JScrollPane(area), BorderLayout.CENTER);

                panel.add(card);
                panel.add(Box.createVerticalStrut(10));
            }

            JScrollPane scroll = new JScrollPane(panel);
            scroll.setPreferredSize(new Dimension(500, 400));
            JOptionPane.showMessageDialog(this, scroll, "Le mie ricette", JOptionPane.PLAIN_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Errore nel caricamento delle tue ricette:\n" + e.getMessage(),
                    "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }



}
