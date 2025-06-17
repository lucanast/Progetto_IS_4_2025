package main;
import Boundary.BoundaryStart;
public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new BoundaryStart().setVisible(true);
        });
    }
}
