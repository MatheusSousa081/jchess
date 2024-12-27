package codes.matheus.player;

import codes.matheus.Color;
import codes.matheus.board.Move;
import codes.matheus.board.Moves;
import org.jetbrains.annotations.NotNull;

public final class Player {
    private @NotNull Username username;
    private @NotNull Color color;
    private final @NotNull Moves moves = new Moves();

    public Player(@NotNull Username username, @NotNull Color color) {
        this.username = username;
        this.color = color;
    }

    public @NotNull Username getUsername() {
        return username;
    }

    public void setUsername(@NotNull Username username) {
        this.username = username;
    }

    public @NotNull Color getColor() {
        return color;
    }

    public void setColor(@NotNull Color color) {
        this.color = color;
    }

    public @NotNull Moves getMoves() {
        return moves;
    }

    public void addMove(@NotNull Move move) {
        moves.add(move);
    }
}
