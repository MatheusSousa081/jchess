package codes.matheus;

import codes.matheus.ui.Window;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
//        @NotNull Game game = new Game();
//        game.run();
        @NotNull Window window = new Window();
        JPanel panel = new JPanel();
        window.add(panel);
    }
}