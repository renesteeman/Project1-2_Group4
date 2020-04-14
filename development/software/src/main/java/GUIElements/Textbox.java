package GUIElements;

import javax.swing.*;
import java.awt.*;

public class Textbox {

    private static final int PAD = 8;
    private static final int WIDTH = 400;
    private static final int HEIGHT = 490;

    public static void showFrame(String title, String message) {
        JFrame frame = createFrame(title);
        JTextField textField = createTextField(message);
        frame.add(textField);
        frame.setVisible(true);
    }

    private static JFrame createFrame(String title) {
        JFrame frame = new JFrame();
        frame.setResizable(false);
        frame.setTitle(title);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return frame;
    }

    private static JTextField createTextField(String errorMessage) {
        JTextField field = new JTextField(errorMessage);
        field.setMargin(new Insets(PAD, PAD, PAD, PAD));
        field.setForeground(Color.RED);
        field.setEditable(true);
        return field;
    }

}
