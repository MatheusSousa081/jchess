package codes.matheus.pieces;

import codes.matheus.Color;
import codes.matheus.board.Board;
import codes.matheus.board.Position;
import org.jetbrains.annotations.NotNull;

public final class Knight extends Piece {
    public Knight(@NotNull Board board, @NotNull Color color) {
        super(board, color);
    }

    private void checkDirection(boolean[][] moves, @NotNull Position currentPosition, int rowIncrement, int columnIncrement) {
        @NotNull Position newPosition = new Position(currentPosition.getRow() + rowIncrement, currentPosition.getColumn() + columnIncrement);
        if (getBoard().positionExists(newPosition) && (!getBoard().thereIsAPiece(newPosition) || isThereOpponentPiece(newPosition))) {
            moves[newPosition.getRow()][newPosition.getColumn()] = true;
        }
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] moves = new boolean[8][8];
        if (getPosition() != null) {
            @NotNull Position currentPosition = getPosition();

            checkDirection(moves, currentPosition, 2, 1);
            checkDirection(moves, currentPosition, 2, -1);
            checkDirection(moves, currentPosition, -2, 1);
            checkDirection(moves, currentPosition, -2, -1);
            checkDirection(moves, currentPosition, 1, 2);
            checkDirection(moves, currentPosition, -1, 2);
            checkDirection(moves, currentPosition, 1, -2);
            checkDirection(moves, currentPosition, -1, -2);
        }

        return moves;
    }

    @Override
    public String toString() {
        return "N";
    }
}
