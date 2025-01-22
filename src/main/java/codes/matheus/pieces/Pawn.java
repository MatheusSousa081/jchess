package codes.matheus.pieces;

import codes.matheus.Color;
import codes.matheus.board.Position;
import codes.matheus.engine.tilemap.TileMap;
import codes.matheus.util.ResourceManager;
import org.jetbrains.annotations.NotNull;

public final class Pawn extends Piece {
    public Pawn(@NotNull Color color, @NotNull TileMap tileMap, @NotNull Pieces pieces) {
        super(color, tileMap, pieces);
        setSprite(color.equals(Color.WHITE) ? ResourceManager.getSprite("pawn-white") : ResourceManager.getSprite("pawn-black"));
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] moves = new boolean[8][8];
        if (getPosition() != null) {
            @NotNull Position currentPosition = getPosition();
            int direction = (getColor() == Color.WHITE) ? -1 : 1;

            //up
            @NotNull Position forward = new Position(currentPosition.getRowMatrix() + direction, currentPosition.getColumnMatrix());
            if (getTileMap().isValidPosition(forward) && !getPieces().contains(forward)) {
                moves[forward.getRowMatrix()][forward.getColumnMatrix()] = true;

                if (getMoveCount() == 0) {
                    forward.move(new Position(currentPosition.getRowMatrix() + 2 * direction, currentPosition.getColumnMatrix()));
                    if (getTileMap().isValidPosition(forward) && !getPieces().contains(forward)) {
                        moves[forward.getRowMatrix()][forward.getColumnMatrix()] = true;
                    }
                }
            }

            //diagonal
            forward.move(new Position(currentPosition.getRowMatrix() + direction, currentPosition.getColumnMatrix() - 1));
            if (getTileMap().isValidPosition(forward) && isThereOpponentPiece(forward)) {
                moves[forward.getRowMatrix()][forward.getColumnMatrix()] = true;
            }

            forward.move(new Position(currentPosition.getRowMatrix() + direction, currentPosition.getColumnMatrix() + 1));
            if (getTileMap().isValidPosition(forward) && isThereOpponentPiece(forward)) {
                moves[forward.getRowMatrix()][forward.getColumnMatrix()] = true;
            }

            //enPassant
            if ((getColor().equals(Color.WHITE) && currentPosition.getRowMatrix() == 3) || (getColor().equals(Color.BLACK) && currentPosition.getRowMatrix() == 4)) {
                forward.move(new Position(currentPosition.getRowMatrix(), currentPosition.getColumnMatrix() - 1));
                if (getTileMap().isValidPosition(forward) && isThereOpponentPiece(forward) && getPieces().getPiece(forward) == getTileMap().getMoveExecutor().getEnPassantVulnerable()) {
                    moves[forward.getRowMatrix() + direction][forward.getColumnMatrix()] = true;
                }

                forward.move(new Position(currentPosition.getRowMatrix(), currentPosition.getColumnMatrix() + 1));
                if (getTileMap().isValidPosition(forward) && isThereOpponentPiece(forward) && getPieces().getPiece(forward) == getTileMap().getMoveExecutor().getEnPassantVulnerable()) {
                    moves[forward.getRowMatrix() + direction][forward.getColumnMatrix()] = true;
                }
            }
        }
        return moves;
    }
}
