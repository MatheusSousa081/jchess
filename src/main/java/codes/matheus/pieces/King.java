package codes.matheus.pieces;

import codes.matheus.Color;
import codes.matheus.board.Board;
import codes.matheus.board.Position;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class King extends Piece {
    public King(@NotNull Board board, @NotNull Color color) {
        super(board, color);
    }

    private void checkDirection(boolean[][] moves, Position currentPosition, int rowIncrement, int colIncrement) {
        @NotNull Position newPosition = new Position(currentPosition.getRow() + rowIncrement, currentPosition.getColumn() + colIncrement);
        if (getBoard().positionExists(newPosition)) {
            if (!getBoard().thereIsAPiece(newPosition) || isThereOpponentPiece(newPosition)) {
                moves[newPosition.getRow()][newPosition.getColumn()] = true;
            }
        }
    }

    private boolean checkRookCastling(@NotNull Position rookPosition) {
        if (!getBoard().positionExists(rookPosition)) {
            return false;
        }
        @Nullable Piece rook = getBoard().getPiece(rookPosition);
        return rook != null && rook instanceof Rook && rook.getColor() == getColor() && rook.getMoveCount() == 0;
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] moves = new boolean[8][8];
        if (getPosition() != null) {
            @NotNull Position currentPosition = getPosition();

            checkDirection(moves, currentPosition, -1, 0);
            checkDirection(moves, currentPosition, 1, 0);
            checkDirection(moves, currentPosition, 0, -1);
            checkDirection(moves, currentPosition, 0, 1);
            checkDirection(moves, currentPosition, -1, -1);
            checkDirection(moves, currentPosition, -1, 1);
            checkDirection(moves, currentPosition, 1, -1);
            checkDirection(moves, currentPosition, 1, 1);

            //RookCastling
            if (getMoveCount() == 0) {
                @NotNull Position rookKing = new Position(currentPosition.getRow(), currentPosition.getColumn() + 3);
                if (checkRookCastling(rookKing)) {
                    if (getBoard().getPiece(new Position(currentPosition.getRow(), currentPosition.getColumn() + 1)) == null && getBoard().getPiece(new Position(currentPosition.getRow(), currentPosition.getColumn() + 2)) == null) {
                        moves[currentPosition.getRow()][currentPosition.getColumn() + 2] = true;
                    }
                }

                @NotNull Position rookQueen = new Position(currentPosition.getRow(), currentPosition.getColumn() - 4);
                if (checkRookCastling(rookQueen)) {
                    if (getBoard().getPiece(new Position(currentPosition.getRow(), currentPosition.getColumn() - 1)) == null && getBoard().getPiece(new Position(currentPosition.getRow(), currentPosition.getColumn() - 2)) == null && getBoard().getPiece(new Position(currentPosition.getRow(), currentPosition.getColumn() - 3)) == null) {
                        moves[currentPosition.getRow()][currentPosition.getColumn() - 2] = true;
                    }
                }
            }
        }
        return moves;
    }

    @Override
    public String toString() {
        return "K";
    }
}
