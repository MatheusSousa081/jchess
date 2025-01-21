package codes.matheus.board;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public final class Moves implements Iterable<@NotNull Move> {
    private final @NotNull List<@NotNull Move> moves;

    public Moves() {
        this.moves = new ArrayList<>();
    }

    public void add(@NotNull Move move) {
        moves.add(move);
    }

    public void remove(@NotNull Move move) {
        moves.add(move);
    }

    @Override
    public @NotNull Iterator<@NotNull Move> iterator() {
        return moves.iterator();
    }
    public @NotNull Stream<@NotNull Move> stream() {
        return moves.stream();
    }

    @Override
    public boolean equals(Object o) {

        if (o == null || getClass() != o.getClass()) return false;
        Moves moves1 = (Moves) o;
        return Objects.equals(moves, moves1.moves);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(moves);
    }
}
