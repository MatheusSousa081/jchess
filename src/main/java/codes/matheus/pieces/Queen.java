package codes.matheus.pieces;

import codes.matheus.Color;
import codes.matheus.board.Position;
import codes.matheus.engine.tilemap.TileMap;
import codes.matheus.util.ResourceManager;
import org.jetbrains.annotations.NotNull;

public final class Queen extends Piece {
    public Queen(@NotNull Color color, @NotNull TileMap tileMap, @NotNull Pieces pieces) {
        super(color, tileMap, pieces);
        setSprite(color.equals(Color.WHITE) ? ResourceManager.getSprite("queen-white") : ResourceManager.getSprite("queen-black"));
    }

    private void checkDirection(boolean[][] moves, @NotNull Position currentPosition, int rowIncrement, int columnIncrement) {
        @NotNull Position newPosition = new Position(currentPosition.getRowMatrix() + rowIncrement, currentPosition.getColumnMatrix() + columnIncrement);
        while (getTileMap().isValidPosition(newPosition)) {
            if (getPieces().contains(newPosition)) {
                if (isThereOpponentPiece(newPosition)) {
                    moves[newPosition.getRowMatrix()][newPosition.getColumnMatrix()] = true;
                }
                break;
            }
            moves[newPosition.getRowMatrix()][newPosition.getColumnMatrix()] = true;
            newPosition = new Position(newPosition.getRowMatrix() + rowIncrement, newPosition.getColumnMatrix() + columnIncrement);
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
            checkDirection(moves, currentPosition, -1, -1);
            checkDirection(moves, currentPosition, -1, 1);
            checkDirection(moves, currentPosition, 1, -1);
            checkDirection(moves, currentPosition, 1, 1);
        }
        return moves;
    }
}
