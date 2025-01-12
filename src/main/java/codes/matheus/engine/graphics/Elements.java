package codes.matheus.engine.graphics;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public final class Elements {
    private static final List<@NotNull Element> elements = new ArrayList<>();

    private Elements() {
        throw new UnsupportedOperationException();
    }

    public static List<@NotNull Element> getElements() {
        return elements;
    }

    public static @NotNull Iterator<@NotNull Element> iterator() {
        return elements.stream().iterator();
    }

    public static @NotNull Stream<@NotNull Element> stream() {
        return elements.stream();
    }
}
