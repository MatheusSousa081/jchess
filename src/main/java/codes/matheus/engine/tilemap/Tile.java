package codes.matheus.engine.tilemap;

import codes.matheus.engine.core.Dimension;
import codes.matheus.engine.core.Vector2D;
import codes.matheus.engine.graphics.Element;
import codes.matheus.engine.graphics.Sprite;
import codes.matheus.pieces.Piece;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.awt.*;
import java.util.Objects;

public final class Tile extends Element {
    private final long id;
    private @NotNull Sprite background;
    private @Nullable Piece piece;

    public Tile(@NotNull Vector2D vector2D, @NotNull Dimension dimension, @NotNull Sprite background, @Nullable Piece piece) {
        super(vector2D, dimension);
        this.id = GenerateId.generate();
        this.background = background;
        this.piece = piece;
    }

    public long getId() {
        return id;
    }

    public @NotNull Sprite getBackground() {
        return background;
    }

    public void setBackground(@NotNull Sprite background) {
        this.background = background;
    }

    public @Nullable Piece getPiece() {
        return piece;
    }

    public void setPiece(@Nullable Piece piece) {
        this.piece = piece;
    }

    @Override
    public void draw(@NotNull Graphics2D graphics2D) {
        background.draw(graphics2D, getVector2D(), getDimension());
        if (piece != null) {
            piece.getSprite().draw(graphics2D, getVector2D(), getDimension());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return id == tile.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    private static class GenerateId {
        @Range(from = 0, to = Long.MAX_VALUE)
        private static long id = 0;

        public static long generate() {
            return id++;
        }
    }
}
