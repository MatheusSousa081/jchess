package codes.matheus.engine.graphics;

import codes.matheus.engine.core.Dimension;
import codes.matheus.engine.core.Vector2D;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.function.Consumer;

public class Button extends Element {
    private final @NotNull String description;
    private @Nullable String text;
    private @Nullable Sprite sprite;
    private @NotNull Consumer<@NotNull Button> event;
    private final @NotNull Color color;

    public Button(@NotNull Vector2D vector2D, @NotNull Dimension dimension, @NotNull String description, @Nullable String text, @Nullable Sprite sprite, @NotNull Color color) {
        super(vector2D, dimension);
        this.description = description;
        this.text = text;
        this.sprite = sprite;
        this.color = color;
    }

    public @NotNull String getDescription() {
        return description;
    }

    public void setText(@Nullable String text) {
        this.text = text;
    }

    public @Nullable Sprite getSprite() {
        return sprite;
    }

    public void setSprite(@Nullable Sprite sprite) {
        this.sprite = sprite;
    }

    public void setEvent(@NotNull Consumer<@NotNull Button> event) {
        this.event = event;
    }

    public void handleClick(@NotNull Vector2D vector2D) {
        if (intersect(vector2D)) {
            event.accept(this);
        }
    }

    @Override
    public void draw(@NotNull Graphics2D graphics2D) {
        graphics2D.setColor(color);
        graphics2D.fillRect(getVector2D().getX(), getVector2D().getY(), getDimension().getWidth(), getDimension().getHeight());

        if (sprite != null) {
            sprite.draw(graphics2D, getVector2D(), getDimension());
        }

        if (text != null) {
            graphics2D.setColor(Color.WHITE);
            graphics2D.setFont(new Font("Arial", Font.PLAIN, Math.min(getDimension().getWidth(), getDimension().getHeight()) / 3));
            @NotNull FontMetrics metrics = graphics2D.getFontMetrics();
            graphics2D.drawString(text, getVector2D().getX() + (getDimension().getWidth() - metrics.stringWidth(text)) / 2, getVector2D().getY() + (getDimension().getHeight() + metrics.getHeight()) / 2 - metrics.getDescent());
        }
    }
}
