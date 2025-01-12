package codes.matheus.engine.ui;

import codes.matheus.engine.core.Dimension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class Window extends JFrame {
    public static @Nullable Window Instance;

    public static @NotNull Window getInstance(@NotNull String title, @NotNull Dimension dimension) {
        if (Instance == null) {
            Instance = new Window(title, dimension);
        }
        return Instance;
    }

    public static @NotNull Window getInstance(@NotNull String title) {
        if (Instance == null) {
            Instance = new Window(title);
        }
        return Instance;
    }

    public static @NotNull Dimension getInitialDimension() {
        return new Dimension(Instance.getWidth(), Instance.getHeight());
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
