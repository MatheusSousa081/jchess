package codes.matheus.pieces;

import codes.matheus.Color;
import codes.matheus.board.Position;
import codes.matheus.engine.core.Dimension;
import codes.matheus.engine.core.Vector2D;
import codes.matheus.engine.graphics.Element;
import codes.matheus.engine.graphics.Sprite;
import codes.matheus.engine.tilemap.TileMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class Piece {
    private final @NotNull TileMap tileMap;
    private final @NotNull Color color;
    private @NotNull Sprite sprite;
    private @Nullable Position position;
    private int moveCount;

    public Piece(@NotNull Color color, @NotNull TileMap tileMap) {
        this.color = color;
        this.tileMap = tileMap;
        moveCount = 0;
    }

    public @Nullable Position getPosition() {
        return position;
    }

    public void setPosition(@Nullable Position position) {
        this.position = position;
    }

    public @NotNull Color getColor() {
        return color;
    }

    public void increaseMoveCount() {
        this.moveCount++;
    }

    public void decreaseMoveCount() {
        this.moveCount--;
    }

    public @NotNull Sprite getSprite() {
        return sprite;
    }

    public void setSprite(@NotNull Sprite sprite) {
        this.sprite = sprite;
    }

    public abstract boolean[][] possibleMoves();

    public boolean hasPossibleMoves() {
        for (int i = 0; i < possibleMoves().length; i++) {
            for (int j = 0; j < possibleMoves().length; j++) {
                if (possibleMoves()[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isThereOpponentPiece(@NotNull Position target) {
        @Nullable Piece piece = tileMap.getTile(target).getPiece();
        return piece != null && piece.getColor() != color;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " " + color;
    }
}