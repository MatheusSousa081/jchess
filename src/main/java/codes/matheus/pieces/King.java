package codes.matheus.pieces;

import codes.matheus.Color;
import codes.matheus.board.Position;
import codes.matheus.engine.tilemap.TileMap;
import codes.matheus.util.ResourceManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class King extends Piece {
    public King(@NotNull Color color, @NotNull TileMap tileMap, @NotNull Pieces pieces) {
        super(color, tileMap, pieces);
        setSprite(color.equals(Color.WHITE) ? ResourceManager.getSprite("king-white") : ResourceManager.getSprite("king-black"));
    }

    private void checkDirection(boolean[][] moves, @NotNull Position currentPosition, int rowIncrement, int columnIncrement) {
        @NotNull Position newPosition = new Position(currentPosition.getRowMatrix() + rowIncrement, currentPosition.getColumnMatrix() + columnIncrement);
        if (getTileMap().isValidPosition(newPosition)) {
            if (!getPieces().contains(newPosition) || isThereOpponentPiece(newPosition)) {
                moves[newPosition.getRowMatrix()][newPosition.getColumnMatrix()] = true;
            }
        }
    }

    private boolean checkRookCastling(@NotNull Position rookPosition) {
        if (!getTileMap().isValidPosition(rookPosition)) {
            return false;
        }
        @Nullable Piece rook = getPieces().getPiece(rookPosition);
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

            //rookCastling
            if (getMoveCount() == 0) {
                @NotNull Position rook = new Position(currentPosition.getRowMatrix(), currentPosition.getColumnMatrix() + 3);
                if (checkRookCastling(rook)) {
                    if (getPieces().getPiece(new Position(currentPosition.getRowMatrix(), currentPosition.getColumnMatrix() + 1)) == null &&
                            getPieces().getPiece(new Position(currentPosition.getRowMatrix(), currentPosition.getColumnMatrix() + 2)) == null) {
                        moves[currentPosition.getRowMatrix()][currentPosition.getColumnMatrix() + 2] = true;
                    }
                }

                rook.move(new Position(currentPosition.getRowMatrix(), currentPosition.getColumnMatrix() - 4));
                if (checkRookCastling(rook)) {
                    if (getPieces().getPiece(new Position(currentPosition.getRowMatrix(), currentPosition.getColumnMatrix() - 1)) == null &&
                            getPieces().getPiece(new Position(currentPosition.getRowMatrix(), currentPosition.getColumnMatrix() - 2)) == null &&
                            getPieces().getPiece(new Position(currentPosition.getRowMatrix(), currentPosition.getColumnMatrix() - 3)) == null) {
                        moves[currentPosition.getRowMatrix()][currentPosition.getColumnMatrix() - 2] = true;
                    }
                }
            }
        }
        return moves;
    }
}
