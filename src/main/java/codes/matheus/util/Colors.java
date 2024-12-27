package codes.matheus.util;

import org.jetbrains.annotations.NotNull;

public final class Colors {
    private Colors() {
        throw new UnsupportedOperationException();
    }

    public static final @NotNull String ANSI_RESET = "\u001B[0m";
    public static final @NotNull String ANSI_BLACK = "\u001B[30m";
    public static final @NotNull String ANSI_WHITE = "\u001B[37m";
    public static final @NotNull String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final @NotNull String ANSI_BLUE_BACKGROUND = "\u001B[44m";
}
