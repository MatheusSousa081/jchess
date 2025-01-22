package codes.matheus.pieces;

import codes.matheus.Color;
import codes.matheus.board.Position;
import codes.matheus.engine.tilemap.TileMap;
import codes.matheus.util.ResourceManager;
import org.jetbrains.annotations.NotNull;

public final class Knight extends Piece {
    public Knight(@NotNull Color color, @NotNull TileMap tileMap, @NotNull Pieces pieces) {
        super(color, tileMap, pieces);
        setSprite(color.equals(Color.WHITE) ? ResourceManager.getSprite("knight-white") : ResourceManager.getSprite("knight-black"));
    }

    public void checkDirection(boolean[][] moves, @NotNull Position currentPosition, int rowIncrement, int columnIncrement) {
        @NotNull Position newPosition = new Position(currentPosition.getRowMatrix() + rowIncrement, currentPosition.getColumnMatrix() + columnIncrement);
        if (getTileMap().isValidPosition(newPosition) && (!getPieces().contains(newPosition) || isThereOpponentPiece(newPosition))) {
            moves[newPosition.getRowMatrix()][newPosition.getColumnMatrix()] = true;
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
}
