package codes.matheus.pieces;

import codes.matheus.Color;
import codes.matheus.board.Position;
import codes.matheus.engine.tilemap.TileMap;
import codes.matheus.util.ResourceManager;
import org.jetbrains.annotations.NotNull;

public final class Rook extends Piece {
    public Rook(@NotNull Color color, @NotNull TileMap tileMap, @NotNull Pieces pieces) {
        super(color, tileMap, pieces);
        setSprite(color.equals(Color.WHITE) ? ResourceManager.getSprite("rook-white") : ResourceManager.getSprite("rook-black"));
    }

    private void checkDirection(boolean[][] moves, @NotNull Position currentPosition, int rowIncrement, int columnIncrement) {
        int row = currentPosition.getRowMatrix() + rowIncrement;
        int column = currentPosition.getColumnMatrix() + columnIncrement;

        while (row >= 0 && row < 8 && column >= 0 && column < 8) {
            @NotNull Position newPosition = new Position(row, column);
            if (!getTileMap().isValidPosition(newPosition)) {
                break;
            }
            if (getPieces().contains(newPosition)) {
                if (isThereOpponentPiece(newPosition)) {
                    moves[newPosition.getRowMatrix()][newPosition.getColumnMatrix()] = true;
                }
                break;
            }
            moves[row][column] = true;
            row += rowIncrement;
            column += columnIncrement;
        }
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
        }
        return moves;
    }
}