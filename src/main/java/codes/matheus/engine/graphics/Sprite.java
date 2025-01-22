package codes.matheus.engine.graphics;

import codes.matheus.engine.core.Dimension;
import codes.matheus.engine.core.Vector2D;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public final class Sprite {
    private final @Nullable BufferedImage image;

    public Sprite(@NotNull String fileName) {
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(@NotNull Graphics2D graphics2D, @NotNull Vector2D vector2D, @NotNull Dimension dimension) {
        graphics2D.drawImage(image, vector2D.getX(), vector2D.getY(), dimension.getWidth(), dimension.getHeight(), null);
    }
}
