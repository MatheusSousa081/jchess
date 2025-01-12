package codes.matheus.engine.core;

import codes.matheus.board.Position;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public class Vector2D {
    @Range(from = 0, to = Long.MAX_VALUE)
    private int x;
    @Range(from = 0, to = Long.MAX_VALUE)
    private int y;

    public Vector2D(@Range(from = 0, to = Long.MAX_VALUE) int x, @Range(from = 0, to = Long.MAX_VALUE) int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D(@NotNull Vector2D vector2D) {
        this.x = vector2D.getX();
        this.y = vector2D.getY();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public @NotNull Vector2D lerp(@NotNull Vector2D target, @Range(from = 0, to = 1) double factor) {
        int newX = (int) (this.x + factor * (target.x - this.x));
        int newY = (int) (this.y + factor * (target.y - this.y));
        return new Vector2D(newX, newY);
    }

    public @NotNull Position toPosition(@NotNull Dimension dimension) {
        return new Position((char) ('a' + (x / (dimension.getWidth() / 8))), 8 - (y / (dimension.getHeight() / 8)));
    }

    @Override
    public String toString() {
        return x + ", " + y;
    }
}
