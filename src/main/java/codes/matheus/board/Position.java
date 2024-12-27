package codes.matheus.board;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public final class Position {
    @Range(from = 0, to = 7)
    private int row;
    @Range(from = 0, to = 7)
    private int column;

    public Position(@Range(from = 0, to = 7) int row, @Range(from = 0, to = 7) int column) {
        this.row = row;
        this.column = column;
    }

    public @Range(from = 0, to = 7) int getColumn() {
        return column;
    }

    public @Range(from = 0, to = 7) int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void move(@Range(from = 0, to = 7) int row, @Range(from = 0, to = 7) int column) {
        this.row = row;
        this.column = column;
    }

    @Override
    public @NotNull String toString() {
        return row + ", " + column;
    }
}
