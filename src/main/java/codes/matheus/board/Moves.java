package codes.matheus.board;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Stream;

public final class Moves implements Iterable<@NotNull Move> {
    private final @NotNull ArrayList<Move> moves;

    public Moves() {
        moves = new ArrayList<>();
    }

    public void add(@NotNull Move move) {
        moves.add(move);
    }

    public void remove(@NotNull Move move) {
        moves.remove(move);
    }

    public @NotNull ArrayList<Move> getMoves() {
        return moves;
    }

    @Override
    public @NotNull Iterator<@NotNull Move> iterator() {
        return moves.iterator();
    }

    public @NotNull Stream<@NotNull Move> stream() {
        return moves.stream();
    }
}
