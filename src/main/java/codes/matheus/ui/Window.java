package codes.matheus.ui;

import javax.swing.*;
import java.awt.*;

public final class Window extends JFrame {
    public Window() throws HeadlessException {
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);
        setFocusable(true);
    }
}
