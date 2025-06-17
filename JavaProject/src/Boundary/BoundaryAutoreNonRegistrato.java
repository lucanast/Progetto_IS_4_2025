package Boundary;

import Control.ControlAutoreNonRegistrato;
import Entity.Autore;
import exception.EmailInUso;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class BoundaryAutoreNonRegistrato extends JFrame {
    private JTextField nomeField, cognomeField, emailField;
    private JPasswordField passwordField;
    private JButton registraButton;
    private JLabel passwordStrengthLabel;

    public BoundaryAutoreNonRegistrato() {
        setTitle("Registrazione Nuovo Autore");
        setSize(400, 350);
        setLayout(new GridLayout(6, 2, 5, 5));

        nomeField = new JTextField();
        cognomeField = new JTextField();
        emailField = new JTextField();
        passwordField = new JPasswordField();
        registraButton = new JButton("Registra");
        passwordStrengthLabel = new JLabel("Password:");
        passwordStrengthLabel.setFont(new Font("SansSerif", Font.ITALIC, 12));

        add(new JLabel("Nome:")); add(nomeField);
        add(new JLabel("Cognome:")); add(cognomeField);
        add(new JLabel("Email:")); add(emailField);
        add(new JLabel("Password:")); add(passwordField);
        add(new JLabel()); add(passwordStrengthLabel);
        add(new JLabel()); add(registraButton);

        passwordField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { aggiornaRobustezza(); }
            public void removeUpdate(DocumentEvent e) { aggiornaRobustezza(); }
            public void changedUpdate(DocumentEvent e) { aggiornaRobustezza(); }
        });

        registraButton.addActionListener(e -> {
            String nome = nomeField.getText().trim();
            String cognome = cognomeField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            Autore autore = new Autore();
            autore.setNome(nome);
            autore.setCognome(cognome);
            autore.setEmail(email);
            autore.setPassword(password);

            ControlAutoreNonRegistrato control = new ControlAutoreNonRegistrato();

            try {
                control.registrazione(autore);
                JOptionPane.showMessageDialog(this, "✅ Registrazione avvenuta con successo!");
                this.dispose();
                new BoundaryAutore().setVisible(true);
            } catch (EmailInUso ex) {
                JOptionPane.showMessageDialog(this,
                        "⚠️ Email già registrata.\nVerrai reindirizzato alla schermata di login.",
                        "Email già in uso", JOptionPane.WARNING_MESSAGE);
                this.dispose();
                new BoundaryAutore().setVisible(true);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this,
                        ex.getMessage(),
                        "Dati non validi", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "❌ Errore durante la registrazione:\n" + ex.getMessage(),
                        "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void aggiornaRobustezza() {
        String pwd = new String(passwordField.getPassword());
        String livello="";
        Color colore=Color.GRAY;

        if (pwd.length() < 8) {
            livello = "Password non ammessa";
            colore = Color.RED;
        } else if (pwd.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\s]).{8,}$")) {
            livello = "Debole";


            colore=Color.orange;
        } else if (pwd.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\s]).{12,}$"))
        {
            livello="Medio";
            colore=Color.yellow;
        }else if (pwd.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\s]).{16,}$")){
            livello="Forte";
            colore=Color.green;
        }

        passwordStrengthLabel.setText("Password: " + livello);
        passwordStrengthLabel.setForeground(colore);
    }
}
