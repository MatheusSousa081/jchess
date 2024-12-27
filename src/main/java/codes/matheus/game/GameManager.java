package codes.matheus.game;

import codes.matheus.Color;
import codes.matheus.board.*;
import codes.matheus.pieces.*;
import codes.matheus.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class GameManager {
    private final @NotNull Board board;
    private final @NotNull MoveExecutor moveExecutor;
    private final @NotNull Player player1;
    private final @NotNull Player player2;
    private @NotNull GameState gameState;
    private @Nullable Player currentPlayer;
    private int turn = 0;
    private final @NotNull Moves historyMoves = new Moves();
    private final @NotNull List<@NotNull Piece> piecesOnBoard = new ArrayList<>();
    private final @NotNull List<@NotNull Piece> capturedPieces = new ArrayList<>();

    public GameManager(@NotNull Board board, @NotNull Player player1, @NotNull Player player2) {
        this.board = board;
        this.moveExecutor = new MoveExecutor(board, this);
        initialSetup();
        this.gameState = GameState.PROGRESS;
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = (player1.getColor() == Color.WHITE) ? player1 : player2;
    }

    public @NotNull MoveExecutor getMoveExecutor() {
        return moveExecutor;
    }

    public @NotNull GameState getGameState() {
        return gameState;
    }

    public void setGameState(@NotNull GameState gameState) {
        this.gameState = gameState;
    }

    public @NotNull List<@NotNull Piece> getCapturedPieces() {
        return capturedPieces;
    }

    public @NotNull Moves getHistoryMoves() {
        return historyMoves;
    }

    public @NotNull List<@NotNull Piece> getPiecesOnBoard() {
        return piecesOnBoard;
    }

    public @Nullable Player getCurrentPlayer() {
        return currentPlayer;
    }

    private void putNewPiece(@NotNull ChessPosition chessPosition, @NotNull Piece piece) {
        board.putPiece(piece, chessPosition.toPosition());
        piecesOnBoard.add(piece);
    }

    public void nextTurn() {
        turn++;
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }

    public void switchTurn() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }

    public void executeMove(@NotNull Move move) {
        @Nullable Piece capturedPiece = moveExecutor.executeMove(move);
        @NotNull Piece piece = move.getPiece();
        piece.increaseMoveCount();
        turn++;

        if (capturedPiece != null) {
            capturedPieces.add(capturedPiece);
            piecesOnBoard.remove(capturedPiece);
            move.setCapturedPiece(capturedPiece);
        }

        historyMoves.add(move);
        nextTurn();
    }

    public @NotNull Player getOpponent(@NotNull Color color) {
        return (color.equals(player1.getColor())) ? player2 : player1;
    }

    public void promotion(@NotNull Piece piece, @NotNull Position position, char newPiece) {
        if (piece instanceof Pawn && (position.getRow() == 0 || position.getRow() == 7)) {
            @NotNull Piece promotedPiece = switch (newPiece) {
                case 'Q' -> new Queen(board, piece.getColor());
                case 'B' -> new Bishop(board, piece.getColor());
                case 'N' -> new Knight(board, piece.getColor());
                case 'R' -> new Rook(board, piece.getColor());
                default -> new Queen(board, piece.getColor());
            };

            board.removePiece(position);
            piecesOnBoard.remove(piece);
            board.putPiece(promotedPiece, position);
            piecesOnBoard.add(promotedPiece);
        }

    }

    public boolean checkDraw() {
        int whiteKings = 0;
        int blackKings = 0;

        for (@NotNull Piece piece : piecesOnBoard) {
            if (piece instanceof King) {
                if (piece.getColor() == Color.WHITE) {
                    whiteKings++;
                } else if (piece.getColor() == Color.BLACK) {
                    blackKings++;
                }
            }
        }

        if (whiteKings == 1 && blackKings == 1 && piecesOnBoard.size() == 2) {
            gameState = GameState.DRAW;
            return true;
        }

        if (gameState != GameState.CHECK) {
            for (@NotNull Piece piece : piecesOnBoard) {
                if (piece.getColor() == Objects.requireNonNull(currentPlayer).getColor()) {
                    boolean[][] moves = piece.possibleMoves();
                    for (int i = 0; i < Board.ROWS; i++) {
                        for (int j = 0; j < Board.COLUMNS; j++) {
                            if (moves[i][j]) {
                                Position target = new Position(i, j);
                                if (!board.thereIsAPiece(target) || Objects.requireNonNull(board.getPiece(target)).getColor() != piece.getColor()) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
            gameState = GameState.DRAW;
            return true;
        }
        return false;
    }

    private void initialSetup() {
        putNewPiece(new ChessPosition('a', 1), new Rook(board, Color.WHITE));
        putNewPiece(new ChessPosition('b', 1), new Knight(board, Color.WHITE));
        putNewPiece(new ChessPosition('c', 1), new Bishop(board, Color.WHITE));
        putNewPiece(new ChessPosition('d', 1), new Queen(board, Color.WHITE));
        putNewPiece(new ChessPosition('e', 1), new King(board, Color.WHITE));
        putNewPiece(new ChessPosition('f', 1), new Bishop(board, Color.WHITE));
        putNewPiece(new ChessPosition('g', 1), new Knight(board, Color.WHITE));
        putNewPiece(new ChessPosition('h', 1), new Rook(board, Color.WHITE));
        putNewPiece(new ChessPosition('a', 2), new Pawn(board, Color.WHITE));
        putNewPiece(new ChessPosition('b', 2), new Pawn(board, Color.WHITE));
        putNewPiece(new ChessPosition('c', 2), new Pawn(board, Color.WHITE));
        putNewPiece(new ChessPosition('d', 2), new Pawn(board, Color.WHITE));
        putNewPiece(new ChessPosition('e', 2), new Pawn(board, Color.WHITE));
        putNewPiece(new ChessPosition('f', 2), new Pawn(board, Color.WHITE));
        putNewPiece(new ChessPosition('g', 2), new Pawn(board, Color.WHITE));
        putNewPiece(new ChessPosition('h', 2), new Pawn(board, Color.WHITE));

        putNewPiece(new ChessPosition('a', 8), new Rook(board, Color.BLACK));
        putNewPiece(new ChessPosition('b', 8), new Knight(board, Color.BLACK));
        putNewPiece(new ChessPosition('c', 8), new Bishop(board, Color.BLACK));
        putNewPiece(new ChessPosition('d', 8), new Queen(board, Color.BLACK));
        putNewPiece(new ChessPosition('e', 8), new King(board, Color.BLACK));
        putNewPiece(new ChessPosition('f', 8), new Bishop(board, Color.BLACK));
        putNewPiece(new ChessPosition('g', 8), new Knight(board, Color.BLACK));
        putNewPiece(new ChessPosition('h', 8), new Rook(board, Color.BLACK));
        putNewPiece(new ChessPosition('a', 7), new Pawn(board, Color.BLACK));
        putNewPiece(new ChessPosition('b', 7), new Pawn(board, Color.BLACK));
        putNewPiece(new ChessPosition('c', 7), new Pawn(board, Color.BLACK));
        putNewPiece(new ChessPosition('d', 7), new Pawn(board, Color.BLACK));
        putNewPiece(new ChessPosition('e', 7), new Pawn(board, Color.BLACK));
        putNewPiece(new ChessPosition('f', 7), new Pawn(board, Color.BLACK));
        putNewPiece(new ChessPosition('g', 7), new Pawn(board, Color.BLACK));
        putNewPiece(new ChessPosition('h', 7), new Pawn(board, Color.BLACK));
    }

    public enum GameState {
        PROGRESS,
        CHECK,
        CHECKMATE,
        DRAW,
    }
}
