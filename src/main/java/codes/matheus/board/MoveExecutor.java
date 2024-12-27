package codes.matheus.board;

import codes.matheus.Color;
import codes.matheus.game.GameManager;
import codes.matheus.pieces.King;
import codes.matheus.pieces.Pawn;
import codes.matheus.pieces.Piece;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public final class MoveExecutor {
    private final @NotNull Board board;
    private final @NotNull GameManager gameManager;
    private @Nullable Piece enPassantVulnerable;

    public MoveExecutor(@NotNull Board board, @NotNull GameManager gameManager) {
        this.board = board;
        this.gameManager = gameManager;
    }

    public @Nullable Piece getEnPassantVulnerable() {
        return enPassantVulnerable;
    }

    public @Nullable Piece executeMove(@NotNull Move move) {
        @NotNull Position originPosition = move.getOrigin().toPosition();
        @NotNull Position targetPosition = move.getTarget().toPosition();
        @Nullable Piece capturedPiece = makeMove(originPosition, targetPosition);


        if (updateCheck(Objects.requireNonNull(gameManager.getCurrentPlayer()).getColor()) && gameManager.getGameState() == GameManager.GameState.CHECK) {
            undoMove(originPosition, targetPosition, capturedPiece);
            System.out.println("Your king is being attacked, remove him from the check");
            gameManager.switchTurn();
        }

        updateCheck(Objects.requireNonNull(gameManager.getOpponent(gameManager.getCurrentPlayer().getColor())).getColor());

        @Nullable Piece piece = board.getPiece(targetPosition);

        if (updateCheckMate(gameManager.getOpponent(gameManager.getCurrentPlayer().getColor()).getColor())) {
            gameManager.setGameState(GameManager.GameState.CHECKMATE);
        }

        if (piece != null) {
            //enPassant
            if (piece instanceof Pawn && (targetPosition.getRow() == originPosition.getRow() - 2 || targetPosition.getRow() == originPosition.getRow() + 2)) {
                enPassantVulnerable = piece;
            } else {
                enPassantVulnerable = null;
            }
        }

        return capturedPiece;
    }

    private @Nullable Piece makeMove(@NotNull Position originPosition, @NotNull Position targetPosition) {
        @Nullable Piece piece = board.removePiece(originPosition);
        @Nullable Piece capturedPiece = board.removePiece(targetPosition);

        if (piece != null) {
            board.putPiece(piece, targetPosition);
        }

        //rookKing
        if (piece instanceof King && targetPosition.getColumn() == originPosition.getColumn() + 2) {
            @Nullable Piece rook = board.removePiece(new Position(originPosition.getRow(), originPosition.getColumn() + 3));
            if (rook != null) {
                board.putPiece(rook, new Position(originPosition.getRow(), originPosition.getColumn() + 1));
                rook.increaseMoveCount();
            }
        }

        //rookQueen
        if (piece instanceof King && targetPosition.getColumn() == originPosition.getColumn() - 2) {
            @Nullable Piece rook = board.removePiece(new Position(originPosition.getRow(), originPosition.getColumn() - 4));
            if (rook != null) {
                board.putPiece(rook, new Position(originPosition.getRow(), originPosition.getColumn() - 1));
                rook.increaseMoveCount();
            }
        }

        //enPassant
        if (piece instanceof Pawn && originPosition.getColumn() != targetPosition.getColumn() && capturedPiece == null) {
            @NotNull Position pawnPosition = (piece.getColor() == Color.WHITE) ? new Position(targetPosition.getRow() + 1, targetPosition.getColumn()) : new Position(targetPosition.getRow() - 1, targetPosition.getColumn());
            capturedPiece = board.removePiece(pawnPosition);
            gameManager.getCapturedPieces().add(Objects.requireNonNull(capturedPiece));
            gameManager.getPiecesOnBoard().remove(capturedPiece);
        }
        return capturedPiece;
    }

    public void undoMove(@NotNull Position origin, @NotNull Position target, @Nullable Piece capturedPiece) {
        @NotNull Piece piece = Objects.requireNonNull(board.removePiece(target));
        piece.decreaseMoveCount();
        board.putPiece(piece, origin);

        if (capturedPiece != null) {
            board.putPiece(capturedPiece, target);
            gameManager.getCapturedPieces().remove(capturedPiece);
            gameManager.getPiecesOnBoard().add(capturedPiece);
        }

        //rookKing
        if (piece instanceof King && target.getColumn() == origin.getColumn() + 2) {
            @Nullable Piece rook = board.removePiece(new Position(origin.getRow(), origin.getColumn() + 1));
            if (rook != null) {
                board.putPiece(rook, new Position(origin.getRow(), origin.getColumn() + 3));
                rook.decreaseMoveCount();
            }
        }

        //rookQueen
        if (piece instanceof King && target.getColumn() == origin.getColumn() - 2) {
            @Nullable Piece rook = board.removePiece(new Position(origin.getRow(), origin.getColumn() - 1));
            if (rook != null) {
                board.putPiece(rook, new Position(origin.getRow(), origin.getColumn() - 4));
                rook.decreaseMoveCount();
            }
        }

        //enPassant
        if (piece instanceof Pawn && origin.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) {
            @NotNull Piece pawn = Objects.requireNonNull(board.removePiece(target));
            @NotNull Position pawnPosition = (piece.getColor() == Color.WHITE) ? new Position(3, target.getColumn()) : new Position(4, target.getColumn());
            board.putPiece(pawn, pawnPosition);
        }

        @NotNull Move move = new Move(ChessPosition.from(origin), ChessPosition.from(target), piece);
        move.setCapturedPiece(capturedPiece);
        gameManager.getHistoryMoves().remove(move);
    }

    public boolean updateCheck(@NotNull Color color) {
        @NotNull Position kingPosition = Objects.requireNonNull(getKing(color).getPosition());
        for (@NotNull Piece piece : gameManager.getPiecesOnBoard()) {
            if (piece.getColor() == gameManager.getOpponent(color).getColor()) {
                boolean[][] moves = piece.possibleMoves();
                if (moves[kingPosition.getRow()][kingPosition.getColumn()]) {
                    gameManager.setGameState(GameManager.GameState.CHECK);
                    return true;
                }
            }
        }
        gameManager.setGameState(GameManager.GameState.PROGRESS);
        return false;
    }

    public boolean updateCheckMate(@NotNull Color color) {
        if (!gameManager.getGameState().equals(GameManager.GameState.CHECK)) {
            return false;
        }

        @NotNull List<@NotNull Piece> list = gameManager.getPiecesOnBoard().stream().filter(piece -> piece.getColor() == color).toList();
        for (@NotNull Piece piece : list) {
            boolean[][] moves = piece.possibleMoves();
            for (int i = 0; i < Board.ROWS; i++) {
                for (int j = 0; j < Board.COLUMNS; j++) {
                    if (moves[i][j]) {
                        @NotNull Position origin = Objects.requireNonNull(piece.getPosition());
                        @NotNull Position target = new Position(i, j);
                        @Nullable Piece capturedPiece = makeMove(origin, target);
                        boolean testCheck = updateCheck(color);
                        undoMove(origin, target, capturedPiece);
                        if (!testCheck) {
                            gameManager.setGameState(GameManager.GameState.PROGRESS);
                            return false;
                        }
                    }
                }
            }
        }
        gameManager.setGameState(GameManager.GameState.CHECKMATE);
        return true;
    }

    private @NotNull Piece getKing(@NotNull Color color) {
        return gameManager.getPiecesOnBoard().stream().filter(king -> king.getColor().equals(color) && king instanceof King).findFirst().orElseThrow();
    }
}
