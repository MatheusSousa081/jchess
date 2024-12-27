package codes.matheus.pieces;

import codes.matheus.Color;
import codes.matheus.board.Board;
import codes.matheus.board.Position;
import org.jetbrains.annotations.NotNull;

public final class Pawn extends Piece {
    public Pawn(@NotNull Board board, @NotNull Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] moves = new boolean[8][8];
        if (getPosition() != null) {
            @NotNull Position currentPosition = getPosition();
            int direction = (getColor() == Color.WHITE) ? -1 : 1;

            //up
            @NotNull Position forward = new Position(currentPosition.getRow() + direction, currentPosition.getColumn());
            if (getBoard().positionExists(forward) && !getBoard().thereIsAPiece(forward)) {
                moves[forward.getRow()][forward.getColumn()] = true;

                if (getMoveCount() == 0) {
                    @NotNull Position doubleForward = new Position(currentPosition.getRow() + 2 * direction, currentPosition.getColumn());
                    if (getBoard().positionExists(doubleForward) && !getBoard().thereIsAPiece(doubleForward)) {
                        moves[doubleForward.getRow()][doubleForward.getColumn()] = true;
                    }
                }
            }

            //diagonal
            @NotNull Position diagonalLeft = new Position(currentPosition.getRow() + direction, currentPosition.getColumn() - 1);
            if (getBoard().positionExists(diagonalLeft) && isThereOpponentPiece(diagonalLeft)) {
                moves[diagonalLeft.getRow()][diagonalLeft.getColumn()] = true;
            }


            @NotNull Position diagonalRight = new Position(currentPosition.getRow() + direction, currentPosition.getColumn() + 1);
            if (getBoard().positionExists(diagonalRight) && isThereOpponentPiece(diagonalRight)) {
                moves[diagonalRight.getRow()][diagonalRight.getColumn()] = true;
            }

            //enPassant
            if ((getColor() == Color.WHITE && currentPosition.getRow() == 3) || (getColor() == Color.BLACK && currentPosition.getRow() == 4)) {
                @NotNull Position enPassantLeft = new Position(currentPosition.getRow(), currentPosition.getColumn() - 1);
                if (getBoard().positionExists(enPassantLeft) && isThereOpponentPiece(enPassantLeft) && getBoard().getPiece(enPassantLeft) == getBoard().getMoveExecutor().getEnPassantVulnerable()) {
                    moves[enPassantLeft.getRow() + direction][enPassantLeft.getColumn()] = true;
                }

                @NotNull Position enPassantRight = new Position(currentPosition.getRow(), currentPosition.getColumn() + 1);
                if (getBoard().positionExists(enPassantRight) && isThereOpponentPiece(enPassantRight) && getBoard().getPiece(enPassantRight) == getBoard().getMoveExecutor().getEnPassantVulnerable()) {
                    moves[enPassantRight.getRow() + direction][enPassantRight.getColumn()] = true;
                }
            }
        }
        return moves;
    }

    @Override
    public String toString() {
        return "P";
    }
}
