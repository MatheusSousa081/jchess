package codes.matheus.board;

import codes.matheus.pieces.Piece;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;

public final class Move {
    public static @NotNull ChessPosition readChessPosition() {
        @NotNull String position = read();
        char column = position.charAt(0);
        int row = Integer.parseInt(position.substring(1));
        return new ChessPosition(column, row);
    }

    private static @NotNull String read() {
        @NotNull BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private final @NotNull ChessPosition origin;
    private final @NotNull ChessPosition target;
    private final @NotNull Piece piece;
    private @Nullable Piece capturedPiece;

    public Move(@NotNull ChessPosition origin, @NotNull ChessPosition target, @NotNull Piece piece) {
        this.origin = origin;
        this.target = target;
        this.piece = piece;
        this.capturedPiece = null;
    }

    public @NotNull ChessPosition getOrigin() {
        return origin;
    }

    public @NotNull ChessPosition getTarget() {
        return target;
    }

    public @NotNull Piece getPiece() {
        return piece;
    }

    public @Nullable Piece getCapturedPiece() {
        return capturedPiece;
    }

    public void setCapturedPiece(@Nullable Piece capturedPiece) {
        this.capturedPiece = capturedPiece;
    }

    @Override
    public String toString() {
        return "Move{" +
                "origin=" + origin +
                ", target=" + target +
                '}';
    }
}
