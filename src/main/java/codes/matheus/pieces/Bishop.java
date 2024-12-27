package codes.matheus.pieces;

import codes.matheus.Color;
import codes.matheus.board.Board;
import codes.matheus.board.Position;
import org.jetbrains.annotations.NotNull;


public final class Bishop extends Piece {
    public Bishop(@NotNull Board board, @NotNull Color color) {
        super(board, color);
    }

    private void checkDirection(boolean[][] moves, @NotNull Position currentPosition, int rowIncrement, int colIncrement) {
        @NotNull Position newPosition = new Position(currentPosition.getRow() + rowIncrement, currentPosition.getColumn() + colIncrement);
        while (getBoard().positionExists(newPosition)) {
            if (getBoard().thereIsAPiece(newPosition)) {
                if (isThereOpponentPiece(newPosition)) {
                    moves[newPosition.getRow()][newPosition.getColumn()] = true;
                }
                break;
            }
            moves[newPosition.getRow()][newPosition.getColumn()] = true;
            newPosition = new Position(newPosition.getRow() + rowIncrement, newPosition.getColumn() + colIncrement);
        }
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] moves = new boolean[8][8];
        if (getPosition() != null) {
            @NotNull Position currentPosition = getPosition();

            checkDirection(moves, currentPosition, -1, -1);
            checkDirection(moves, currentPosition, -1, 1);
            checkDirection(moves, currentPosition, 1, -1);
            checkDirection(moves, currentPosition, 1, 1);
        }
        return moves;
    }

    @Override
    public String toString() {
        return "B";
    }
}
