package codes.matheus.pieces;

import codes.matheus.Color;
import codes.matheus.board.Board;
import codes.matheus.board.Position;
import org.jetbrains.annotations.NotNull;

public final class Rook extends Piece {
    public Rook(@NotNull Board board, @NotNull Color color) {
        super(board, color);
    }

    private void checkDirectionVertical(boolean[][] moves, @NotNull Position currentPosition, int rowIncrement) {
        int row = currentPosition.getRow() + rowIncrement;
        while (row >= 0 && row < 8) {
            @NotNull Position newPosition = new Position(row, currentPosition.getColumn());
            if (!getBoard().positionExists(newPosition)) {
                break;
            }

            if (getBoard().thereIsAPiece(newPosition)) {
                if (isThereOpponentPiece(newPosition)) {
                    moves[newPosition.getRow()][newPosition.getColumn()] = true;
                }
                break;
            }
            moves[row][currentPosition.getColumn()] = true;
            row += rowIncrement;
        }
    }

    private void checkDirectionHorizontal(boolean[][] moves, @NotNull Position currentPosition, int columnIncrement) {
        int column = currentPosition.getColumn() + columnIncrement;
        while (column >= 0 && column < 8) {
            @NotNull Position newPosition = new Position(currentPosition.getRow(), column);
            if (!getBoard().positionExists(newPosition)) {
                break;
            }

            if (getBoard().thereIsAPiece(newPosition)) {
                if (isThereOpponentPiece(newPosition)) {
                    moves[newPosition.getRow()][newPosition.getColumn()] = true;
                }
                break;
            }
            moves[currentPosition.getRow()][column] = true;
            column += columnIncrement;
        }
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] moves = new boolean[8][8];
        if (getPosition() != null) {
            @NotNull Position currentPosition = getPosition();

            checkDirectionVertical(moves, currentPosition, -1);
            checkDirectionVertical(moves, currentPosition, 1);

            checkDirectionHorizontal(moves, currentPosition, -1);
            checkDirectionHorizontal(moves, currentPosition, 1);
        }
        return moves;
    }

    @Override
    public String toString() {
        return "R";
    }


}
