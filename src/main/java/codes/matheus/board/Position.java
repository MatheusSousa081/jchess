package codes.matheus.board;

import org.jetbrains.annotations.Range;

public final class Location {
    @Range(from = 1, to = 8)
    private int row;
    @Range(from = 1, to = 8)
    private int column;

    public Location(@Range(from = 1, to = 8) int row, @Range(from = 1, to = 8) int column) {
        this.row = row;
        this.column = column;
    }

    public @Range(from = 1, to = 8) int getColumn() {
        return column;
    }

    public @Range(from = 1, to = 8) int getRow() {
        return row;
    }

    public void move(@Range(from = 1, to = 8) int row, @Range(from = 1, to = 8) int column) {
        this.row = row;
        this.column = column;
    }

    @Override
    public String toString() {
        return row + ", " + column;
    }
}
