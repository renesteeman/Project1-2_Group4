package GUI.Menu;

import javax.swing.*;
import java.awt.*;

public class MainMenu {

    private JFrame frame;
    private JPanel panel;
    private JButton button;

    public MainMenu() {

        frame = new JFrame();

        button = new JButton("Play game!");

        panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        panel.add(button);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Crazy Putting!");
        frame.setVisible(true);
    }

    public static void createMenu(){
        new MainMenu();
    }
}
