package codes.matheus.pieces;

import codes.matheus.Color;
import codes.matheus.board.Position;
import codes.matheus.engine.graphics.Sprite;
import codes.matheus.engine.tilemap.TileMap;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public final class Knight extends Piece {
    public Knight(@NotNull Color color, @NotNull TileMap tileMap, @NotNull Pieces pieces) {
        super(color, tileMap, pieces);
        setSprite(new Sprite((color.equals(Color.WHITE)) ? new File("src/main/resources/pieces/knight-white.png") : new File("src/main/resources/pieces/knight-black.png") ));
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
