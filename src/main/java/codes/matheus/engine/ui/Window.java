package codes.matheus.engine.ui;

import codes.matheus.engine.core.Dimension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class Window extends JFrame {
    public static @Nullable Window instance;

    public static @NotNull Window getInstance(@NotNull String title, @NotNull Dimension dimension) {
        if (instance == null) {
            instance = new Window(title, dimension);
        }
        return instance;
    }

    public static @NotNull Window getInstance(@NotNull String title) {
        if (instance == null) {
            instance = new Window(title);
        }
        return instance;
    }

    public static @NotNull Dimension getInitialDimension() {
        return new Dimension(instance.getWidth(), instance.getHeight());
    }

    private Window(@NotNull String title) {
        initialize(title, Dimension.Resolutions.WINDOW_FULL.getDimension());
    }

    private Window(@NotNull String title, @NotNull Dimension dimension) {
        initialize(title, dimension);
    }

    private void initialize(@NotNull String title, @NotNull Dimension dimension) {
        setTitle(title);
        setSize(dimension.getWidth(), dimension.getHeight());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
    }
}
