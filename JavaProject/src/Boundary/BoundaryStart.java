package Boundary;

import javax.swing.*;
import java.awt.*;

public class BoundaryStart extends JFrame {

    public BoundaryStart() {
        setTitle("SISTEMA DI CONDIVISIONE RICETTE");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Titolo in alto
        JLabel titolo = new JLabel("Benvenuti nel sistema di condivisioni di ricette", SwingConstants.CENTER);
        titolo.setFont(new Font("SansSerif", Font.BOLD, 16));
        titolo.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(titolo, BorderLayout.NORTH);

        // Pulsanti centrali
        JButton btnLogin = new JButton("Login");
        JButton btnRegistrati = new JButton("Registrati");

        btnLogin.addActionListener(e -> {
            new BoundaryAutore().setVisible(true);
            dispose(); // chiude la finestra corrente
        });

        btnRegistrati.addActionListener(e -> {
            new BoundaryAutoreNonRegistrato().setVisible(true);
            dispose();
        });

        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 80, 20, 80));
        centerPanel.add(btnLogin);
        centerPanel.add(btnRegistrati);

        add(centerPanel, BorderLayout.CENTER);

        // Firma in basso a destra
        JLabel firma = new JLabel("PROGETTO IS REALIZZATO DA AMOROSO MICHELE PIO, AMOROSO LUIGI E ANASTASIO LUCA", SwingConstants.RIGHT);
        firma.setFont(new Font("SansSerif", Font.ITALIC, 7));
        firma.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(firma, BorderLayout.SOUTH);
    }
}
