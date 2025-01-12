package codes.matheus.board;

import codes.matheus.engine.tilemap.TileMap;
import codes.matheus.game.GameManager;
import codes.matheus.pieces.Piece;
import codes.matheus.pieces.Pieces;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class MoveExecutor {
    private final @NotNull Pieces pieces;
    private final @NotNull TileMap tileMap;
    private final @NotNull GameManager manager;

    public MoveExecutor(@NotNull Pieces pieces, @NotNull TileMap tileMap, @NotNull GameManager manager) {
        this.pieces = pieces;
        this.tileMap = tileMap;
        this.manager = manager;
    }

    public void execute(@NotNull Move move) {
        @Nullable Piece capturedPiece = makeMove(move.getOrigin(), move.getTarget());
        move.setCapturedPiece(capturedPiece);
        move.getPiece().increaseMoveCount();

        if (capturedPiece != null) {
            capturedPiece.decreaseMoveCount();
        }
    }

    private @Nullable Piece makeMove(@NotNull Position origin, @NotNull Position target) {
        @Nullable Piece piece = pieces.removePiece(tileMap, origin);
        @Nullable Piece capturedPiece = pieces.removePiece(tileMap, target);

        if (piece != null) {
            pieces.put(tileMap, piece, target);
        }

        return capturedPiece;
    }
}
