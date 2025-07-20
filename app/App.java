package app;

import view.BienvenidaView;

public class App {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new BienvenidaView().setVisible(true);
        });
    }
}
