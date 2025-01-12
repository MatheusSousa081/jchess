package codes.matheus.engine.core;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.awt.*;

public final class Dimension {
    @Range(from = 0, to = 3840)
    private int width;
    @Range(from = 0, to = 2160)
    private int height;

    public Dimension(@Range(from = 0, to = 3840) int width, @Range(from = 0, to = 2160) int height) {
        this.width = width;
        this.height = height;
    }

    public Dimension(@NotNull Dimension dimension) {
        this.width = dimension.getWidth();
        this.height = dimension.getHeight();
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(@Range(from = 0, to = 3840) int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(@Range(from = 0, to = 2160) int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "(" + width + ", " + height + ")";
    }

    public enum Resolutions {
        SXGA(new Dimension(1280, 1024)),
        HD(new Dimension(1366, 768)),
        FHD(new Dimension(1920, 1080)),
        WUXGA(new Dimension(1920, 1200)),
        QHD(new Dimension(2560, 1440)),
        WQHD(new Dimension(3440, 1440)),
        UHD(new Dimension(3840, 2160)),
        WINDOW_FULL(new Dimension(getFullScreenDimension()));

        private final @NotNull Dimension dimension;

        Resolutions(@NotNull Dimension dimension) {
            this.dimension = dimension;
        }

        public @NotNull Dimension getDimension() {
            return dimension;
        }

        private static @NotNull Dimension getFullScreenDimension() {
            java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            return new Dimension((int) screenSize.getWidth(), (int) screenSize.getHeight());
        }
    }
}
