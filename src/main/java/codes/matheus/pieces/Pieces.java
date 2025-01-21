package codes.matheus.pieces;

import codes.matheus.board.Position;
import codes.matheus.engine.tilemap.TileMap;
import codes.matheus.exception.BoardException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Stream;

public class Pieces implements Iterable<@NotNull Piece> {
    private final @NotNull List<@NotNull Piece> pieces = Collections.synchronizedList(new ArrayList<>());

    public Pieces() {
    }

    public synchronized void put(@NotNull TileMap tileMap, @NotNull Piece piece, @NotNull Position position) {
        if (contains(position)) {
            throw new BoardException("There is already a piece on position");
        }
        if (!tileMap.isValidPosition(position)) {
            throw new BoardException("Invalid position on TileMap");
        }
        tileMap.getTile(position).setPiece(piece);
        piece.setPosition(position);
        pieces.add(piece);
    }

    public synchronized @Nullable Piece removePiece(@NotNull TileMap tileMap, @NotNull Position position) {
        if (!contains(position)) {
            return null;
        }

        @Nullable Piece piece = tileMap.getTile(position).getPiece();
        if (piece != null) {
            piece.setPosition(null);
            tileMap.getTile(position).setPiece(null);
            pieces.remove(piece);
        }
        return piece;
    }

    public @Nullable Piece getPiece(@NotNull Position position) {
        return pieces.stream().filter(piece -> piece.getPosition().equals(position)).findFirst().orElse(null);
    }

    public boolean contains(@NotNull Position position) {
        return pieces.stream().anyMatch(piece -> position.equals(piece.getPosition()));
    }

    public int size() {
        return pieces.size();
    }

    @Override
    public @NotNull Iterator<Piece> iterator() {
        return stream().iterator();
    }

    public @NotNull Stream<@NotNull Piece> stream() {
        return pieces.stream();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pieces pieces1 = (Pieces) o;
        return Objects.equals(pieces, pieces1.pieces);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(pieces);
    }
}