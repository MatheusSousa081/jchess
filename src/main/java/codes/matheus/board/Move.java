package codes.matheus.board;

import codes.matheus.pieces.Piece;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class Move {
    private final @NotNull Position origin;
    private final @NotNull Position target;
    private @NotNull Piece piece;
    private @Nullable Piece capturedPiece;
    private @Nullable Piece promotedPiece;

    public Move(@NotNull Position origin, @NotNull Position target, @NotNull Piece piece) {
        this.origin = origin;
        this.target = target;
        this.piece = piece;
    }

    public @NotNull Position getOrigin() {
        return origin;
    }

    public @NotNull Position getTarget() {
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

    public @Nullable Piece getPromotedPiece() {
        return promotedPiece;
    }

    public void promote(@NotNull Piece promotedPiece) {
        this.promotedPiece = promotedPiece;
    }

    @Override
    public String toString() {
        return "Move{" +
                "origin=" + origin +
                ", target=" + target +
                ", piece=" + piece +
                ", capturedPiece=" + capturedPiece +
                ", promotedPiece=" + promotedPiece +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return Objects.equals(origin, move.origin) && Objects.equals(target, move.target) && Objects.equals(piece, move.piece) && Objects.equals(capturedPiece, move.capturedPiece) && Objects.equals(promotedPiece, move.promotedPiece);
    }

    @Override
    public int hashCode() {
        return Objects.hash(origin, target, piece, capturedPiece, promotedPiece);
    }
}