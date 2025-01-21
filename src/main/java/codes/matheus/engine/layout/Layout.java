package codes.matheus.engine.layout;

import codes.matheus.engine.core.Dimension;
import codes.matheus.engine.core.Vector2D;
import codes.matheus.engine.graphics.Element;
import org.jetbrains.annotations.NotNull;

public interface Layout {
    @NotNull Dimension getDimensionReference(@NotNull Element element);

    @NotNull Vector2D getVector2DReference(@NotNull Element element);
}
