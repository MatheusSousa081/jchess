package codes.matheus.engine.graphics;

import codes.matheus.engine.core.Dimension;
import codes.matheus.engine.core.Vector2D;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class Sprite {
    private @NotNull BufferedImage image;

    public Sprite(@NotNull File file) {
        try {
            this.image = ImageIO.read(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public @NotNull BufferedImage getImage() {
        return image;
    }

    public void setImage(@NotNull File file) {
        try {
            this.image = ImageIO.read(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(@NotNull Graphics2D graphics2D, @NotNull Vector2D vector2D, @NotNull Dimension dimension) {
        graphics2D.drawImage(image, vector2D.getX(), vector2D.getY(), dimension.getWidth(), dimension.getHeight(), null);
    }
}
