package codes.matheus.player;

import org.jetbrains.annotations.NotNull;

public final class Username implements CharSequence {
    public static boolean isValid(@NotNull String value) {
        if (value.length() <= 3 || value.length() > 20) {
            return false;
        } if (value.isBlank()) {
            return false;
        } if (!value.matches("^[0-9a-zA-Z_.-]+$")) {
            return false;
        }
        return true;
    }

    public static @NotNull Username parse(@NotNull String value) {
        if (!isValid(value)) {
            throw new IllegalArgumentException("The username inserted is invalid! It must have at least 5 characters and a maximum of 20");
        }
        return new Username(value);
    }

    private @NotNull String value;

    public Username(@NotNull String value) {
        if (!isValid(value)) {
            throw new IllegalArgumentException("The username inserted is invalid! It must have at least 5 characters and a maximum of 20");
        }
        this.value = value;
    }

    @Override
    public int length() {
        return value.length();
    }

    @Override
    public char charAt(int i) {
        return value.charAt(i);
    }

    @Override
    public @NotNull CharSequence subSequence(int i, int i1) {
        return value.subSequence(i, i1);
    }

    @Override
    public @NotNull String toString() {
        return value;
    }
}
