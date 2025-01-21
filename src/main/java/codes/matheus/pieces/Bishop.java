package codes.matheus.pieces;

import codes.matheus.Color;
import codes.matheus.board.Position;
import codes.matheus.engine.graphics.Sprite;
import codes.matheus.engine.tilemap.TileMap;
import org.jetbrains.annotations.NotNull;

import java.io.File;


public final class Bishop extends Piece {
    public Bishop(@NotNull Color color, @NotNull TileMap tileMap, @NotNull Pieces pieces) {
        super(color, tileMap, pieces);
        setSprite(new Sprite((color.equals(Color.WHITE)) ? new File("src/main/resources/pieces/bishop-white.png") : new File("src/main/resources/pieces/bishop-black.png") ));
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

            checkDirection(moves, currentPosition, -1, -1);
            checkDirection(moves, currentPosition, -1, 1);
            checkDirection(moves, currentPosition, 1, -1);
            checkDirection(moves, currentPosition, 1, 1);
        }
        return moves;
    }
}
