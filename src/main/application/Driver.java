package application;

import javax.swing.*;
import java.util.Objects;

public class Driver {

    protected static JFrame window;

    public static void main(String[] args) {

        window = new JFrame();

        // WINDOW PROPERTIES
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("POKéMON: Let's Battle!");
        new Driver().setIcon();

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        // LOAD SETTINGS
        gamePanel.config.loadConfig();
        if (gamePanel.fullScreenOn) {
            window.setUndecorated(true);
        }

        // RESIZE WINDOW
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        // START
        gamePanel.setupGame();
        gamePanel.startGameThread();
    }

    private void setIcon() {
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(
                getClass().getClassLoader().getResource("misc/pokemon.png"))
        );
        window.setIconImage(icon.getImage());
    }
}