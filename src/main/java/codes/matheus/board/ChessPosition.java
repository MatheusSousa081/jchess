package codes.matheus.game;

import codes.matheus.board.Position;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public class ChessPosition {
    public static @NotNull ChessPosition from(@NotNull Position position) {
        return new ChessPosition((char) ('a' + position.getColumn()), 8 - position.getRow());

    }

    private char column;
    @Range(from = 1, to = 8)
    private int row;

    public ChessPosition(char column, @Range(from = 1, to = 8) int row) {
        this.column = column;
        this.row = row;
    }

    public char getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public @NotNull Position toPosition() {
        return new Position(8 - row, column - 'a');
    }

    @Override
    public @NotNull String toString() {
        return column + "" + row;
    }
}
