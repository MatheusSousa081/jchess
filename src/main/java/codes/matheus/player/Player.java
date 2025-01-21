package codes.matheus.player;

import codes.matheus.Color;
import codes.matheus.board.Move;
import codes.matheus.board.Moves;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Player {
    private @NotNull Username username;
    private @Nullable Color color;

    public Player(@NotNull Username username) {
        this.username = username;
    }

    public @NotNull Username getUsername() {
        return username;
    }

    public void setUsername(@NotNull Username username) {
        this.username = username;
    }

    public @Nullable Color getColor() {
        return color;
    }

    public void setColor(@Nullable Color color) {
        this.color = color;
    }
}
