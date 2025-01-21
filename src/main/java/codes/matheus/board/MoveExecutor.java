package codes.matheus.board;

import codes.matheus.Color;
import codes.matheus.engine.tilemap.TileMap;
import codes.matheus.game.GameManager;
import codes.matheus.pieces.King;
import codes.matheus.pieces.Pawn;
import codes.matheus.pieces.Piece;
import codes.matheus.pieces.Pieces;
import codes.matheus.util.Promotion;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public final class MoveExecutor {
    private final @NotNull Pieces pieces;
    private final @NotNull TileMap tileMap;
    private final @NotNull GameManager manager;
    private @Nullable Piece enPassantVulnerable;

    public MoveExecutor(@NotNull Pieces pieces, @NotNull TileMap tileMap, @NotNull GameManager manager) {
        this.pieces = pieces;
        this.tileMap = tileMap;
        this.manager = manager;
    }

    public @Nullable Piece getEnPassantVulnerable() {
        return enPassantVulnerable;
    }

    public void execute(@NotNull Move move) {
        @Nullable Piece capturedPiece = makeMove(move.getOrigin(), move.getTarget());
        @NotNull Piece piece = move.getPiece();
        move.setCapturedPiece(capturedPiece);
        piece.increaseMoveCount();

        if (piece instanceof Pawn && (move.getTarget().getRowMatrix() == 0 || move.getTarget().getRowMatrix() == 7)) {
            Promotion.promote(tileMap, pieces, move);
        }

        if (updateCheck(Objects.requireNonNull(manager.getCurrentPlayer())) && manager.getGameState() == GameManager.GameState.CHECK) {
            undoMove(move.getOrigin(), move.getTarget(), move.getCapturedPiece());
            manager.setCurrentPlayer((manager.getCurrentPlayer() == Color.WHITE) ? Color.BLACK : Color.WHITE);
        }
        updateCheck(Objects.requireNonNull(manager.getOpponent(manager.getCurrentPlayer())));

        if (updateCheckMate(manager.getOpponent(manager.getCurrentPlayer()))) {
            System.out.println("CheckMate");
            manager.setGameState(GameManager.GameState.CHECKMATE);
        }

        //enPassant
        if (piece instanceof Pawn && (move.getTarget().getRowMatrix() == move.getOrigin().getRowMatrix() - 2 || move.getTarget().getRowMatrix() == move.getOrigin().getRowMatrix() + 2)) {
            enPassantVulnerable = piece;
        } else {
            enPassantVulnerable = null;
        }

        if (checkDraw()) {
            manager.setGameState(GameManager.GameState.DRAW);
            System.out.println("Draw");
        }
    }

    private @Nullable Piece makeMove(@NotNull Position origin, @NotNull Position target) {
        @Nullable Piece piece = pieces.removePiece(tileMap, origin);
        @Nullable Piece capturedPiece = pieces.removePiece(tileMap, target);

        if (piece != null) {
            pieces.put(tileMap, piece, target);
        }

        //rookKing
        if (piece instanceof King && target.getColumnMatrix() == origin.getColumnMatrix() + 2) {
            @Nullable Piece rook = pieces.removePiece(tileMap, new Position(origin.getRowMatrix(), origin.getColumnMatrix() + 3));
            if (rook != null) {
                pieces.put(tileMap, rook, new Position(origin.getRowMatrix(), origin.getColumnMatrix() + 1));
                rook.increaseMoveCount();
            }
        }

        //rookQueen
        if (piece instanceof King && target.getColumnMatrix() == origin.getColumnMatrix() - 2) {
            @Nullable Piece rook = pieces.removePiece(tileMap, new Position(origin.getRowMatrix(), origin.getColumnMatrix() - 4));
            if (rook != null) {
                pieces.put(tileMap, rook, new Position(origin.getRowMatrix(), origin.getColumnMatrix() - 1));
                rook.increaseMoveCount();
            }
        }

        //enPassant
        if (piece instanceof Pawn && origin.getColumnMatrix() != target.getColumnMatrix() && capturedPiece == null) {
            @NotNull Position newPosition = (piece.getColor().equals(Color.WHITE)) ? new Position(target.getRowMatrix() + 1, target.getColumnMatrix()) : new Position(target.getRowMatrix() - 1, target.getColumnMatrix());
            capturedPiece = pieces.removePiece(tileMap, newPosition);
        }

        return capturedPiece;
    }

    private void undoMove(@NotNull Position origin, @NotNull Position target, @Nullable Piece capturedPiece) {
        @NotNull Piece piece = Objects.requireNonNull(pieces.removePiece(tileMap, target));
        piece.decreaseMoveCount();
        pieces.put(tileMap, piece, origin);

        if (capturedPiece != null) {
            pieces.put(tileMap, capturedPiece, target);
        }

        //rookKing
        if (piece instanceof King && target.getColumnMatrix() == origin.getColumnMatrix() + 2) {
            @Nullable Piece rook = pieces.removePiece(tileMap, new Position(origin.getRowMatrix(), origin.getColumnMatrix() + 1));
            if (rook != null) {
                pieces.put(tileMap, rook, new Position(origin.getRowMatrix(), origin.getColumnMatrix() + 3));
                rook.decreaseMoveCount();
            }
        }

        //rookQueen
        if (piece instanceof King && target.getColumnMatrix() == origin.getColumnMatrix() - 2) {
            @Nullable Piece rook = pieces.removePiece(tileMap, new Position(origin.getRowMatrix(), origin.getColumnMatrix() - 1));
            if (rook != null) {
                pieces.put(tileMap, rook, new Position(origin.getRowMatrix(), origin.getColumnMatrix() - 4));
                rook.decreaseMoveCount();
            }
        }

        //enPassant
        if (piece instanceof Pawn && origin.getColumnMatrix() != target.getColumnMatrix() && capturedPiece == enPassantVulnerable) {
            @NotNull Piece pawn = Objects.requireNonNull(pieces.removePiece(tileMap, target));
            @NotNull Position pawnPosition = (piece.getColor() == Color.WHITE) ? new Position(3, target.getColumnMatrix()) : new Position(4, target.getColumnMatrix());
            pieces.put(tileMap, pawn, pawnPosition);
        }
    }

    private @NotNull Piece getKing(@NotNull Color color) {
        return pieces.stream().filter(king -> king.getColor().equals(color) && king instanceof King).findFirst().orElseThrow();
    }

    private boolean updateCheck(@NotNull Color color) {
        @Nullable Position kingPosition = Objects.requireNonNull(getKing(color)).getPosition();
        for (@NotNull Piece piece : pieces) {
            if (piece.getColor() == manager.getOpponent(color)) {
                boolean[][] moves = piece.possibleMoves();
                if (moves[kingPosition.getRowMatrix()][kingPosition.getColumnMatrix()]) {
                    manager.setGameState(GameManager.GameState.CHECK);
                    System.out.println("Check");
                    return true;
                }
            }
        }
        manager.setGameState(GameManager.GameState.PROGRESS);
        return false;
    }

    private boolean updateCheckMate(@NotNull Color color) {
        if (!manager.getGameState().equals(GameManager.GameState.CHECK)) {
            return false;
        }

        @NotNull List<@NotNull Piece> list = pieces.stream().filter(piece -> piece.getColor() == color).toList();
        for (@NotNull Piece piece : list) {
            boolean[][] moves = piece.possibleMoves();
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (moves[i][j]) {
                        @NotNull Position origin = Objects.requireNonNull(piece.getPosition());
                        @NotNull Position target = new Position(i, j);
                        @Nullable Piece capturedPiece = makeMove(origin, target);
                        boolean testCheck = updateCheck(color);
                        undoMove(origin, target, capturedPiece);
                        if (!testCheck) {
                            manager.setGameState(GameManager.GameState.PROGRESS);
                            return false;
                        }
                    }
                }
            }
        }
        manager.setGameState(GameManager.GameState.CHECKMATE);
        return true;
    }

    private boolean checkDraw() {
        if (pieces.stream().allMatch(piece -> piece instanceof King) && pieces.size() == 2) {
            return true;
        } else if (!pieces.stream().anyMatch(Piece::hasPossibleMoves)) {
            return true;
        }
        return false;
    }
}
