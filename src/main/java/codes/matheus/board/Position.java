package codes.matheus.board;

import codes.matheus.engine.core.Vector2D;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.Objects;

public final class Position {
    private char column;
    @Range(from = 1, to = 8)
    private int row;

    public Position(char column, @Range(from = 1, to = 8) int row) {
        this.column = column;
        this.row = row;
    }

    public Position(@Range(from = 0, to = 7) int row, @Range(from = 0, to = 7) int column) {
        this.column = (char) ('a' + column);
        this.row = 8 - row;
    }


    public void move(char column, @Range(from = 1, to = 8) int row) {
        this.column = column;
        this.row = row;
    }

    public @NotNull Vector2D toVector2D(int tileSize) {
        return new Vector2D((column - 'a') * tileSize, (8 - row) * tileSize);
    }

    public int[] toMatrix() {
        return new int[] {
                8 - row,
                column - 'a'
        };
    }

    @Override
    public @NotNull String toString() {
        return column + "" + row;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return column == position.column && row == position.row;
    }

    @Override
    public int hashCode() {
        return Objects.hash(column, row);
    }
}