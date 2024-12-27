package codes.matheus.pieces;

import codes.matheus.Color;
import codes.matheus.board.Board;
import codes.matheus.board.Position;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class Piece {
    protected @Nullable Position position;
    private final @NotNull Board board;
    private final @NotNull Color color;
    private int moveCount;

    public Piece(@NotNull Board board, @NotNull Color color) {
        this.board = board;
        this.color = color;
        this.moveCount = 0;
    }

    public @Nullable Position getPosition() {
        return position;
    }

    public void setPosition(@Nullable Position position) {
        this.position = position;
    }

    public @NotNull Board getBoard() {
        return board;
    }

    public @NotNull Color getColor() {
        return color;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void increaseMoveCount() {
        moveCount++;
    }

    public void decreaseMoveCount() {
        moveCount--;
    }

    public abstract boolean[][] possibleMoves();

    public boolean hasPossibleMove() {
        for (int i = 0; i < possibleMoves().length; i++) {
            for (int j = 0; j < possibleMoves()[0].length; j++) {
                if (possibleMoves()[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isThereOpponentPiece(@NotNull Position position) {
        @Nullable Piece piece = board.getPiece(position);
        return piece != null && piece.getColor() != getColor();
    }


}
