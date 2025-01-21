package codes.matheus.engine.layout;

import codes.matheus.engine.core.Dimension;
import codes.matheus.engine.core.Vector2D;
import codes.matheus.engine.graphics.Element;
import codes.matheus.engine.ui.ResponsivePanel;
import codes.matheus.engine.ui.Window;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public final class ResponsiveLayout implements Layout {
    private final @NotNull ResponsivePanel responsivePanel;
    private final @NotNull Dimension windowsReference = Window.getInitialDimension();
    private @NotNull Dimension windowsMovementReference = windowsReference;
    private final @NotNull ConcurrentHashMap<@NotNull Element, @NotNull Dimension> mapDimensionReference;
    private final @NotNull ConcurrentHashMap<@NotNull Element, @NotNull Vector2D> mapLocationReference;

    public ResponsiveLayout(@NotNull ResponsivePanel responsivePanel) {
        this.responsivePanel = responsivePanel;
        this.mapDimensionReference = new ConcurrentHashMap<>();
        this.mapLocationReference = new ConcurrentHashMap<>();
    }

    public void addReference(@NotNull Element element) {
        mapDimensionReference.put(element, element.getDimension());
        mapLocationReference.put(element, element.getVector2D());
    }

    public void addAllReferences(@NotNull List<@NotNull Element> elements) {
        for (@NotNull Element element : elements) {
            addReference(element);
        }
    }

    @Override
    public @NotNull Dimension getDimensionReference(@NotNull Element element) {
        return mapDimensionReference.get(element);
    }

    @Override
    public @NotNull Vector2D getVector2DReference(@NotNull Element element) {
        return mapLocationReference.get(element);
    }

    private int calculateX(@NotNull Vector2D vector2D) {
        return (vector2D.getX() * Window.getInitialDimension().getWidth() / windowsMovementReference.getWidth());
    }

    private int calculateY(@NotNull Vector2D vector2D) {
        return (vector2D.getY() * Window.getInitialDimension().getHeight() / windowsMovementReference.getHeight());
    }

    private int calculateWidth(@NotNull Dimension dimension) {
        return (dimension.getWidth() * Window.getInitialDimension().getWidth() / windowsReference.getWidth());
    }

    private int calculateHeight(@NotNull Dimension dimension) {
        return (dimension.getHeight() * Window.getInitialDimension().getHeight() / windowsReference.getHeight());
    }

    public void resizeComponents(@NotNull List<@NotNull Element> elements) {
        for (@NotNull Element element : elements) {
            element.setDimension(new Dimension(calculateWidth(getDimensionReference(element)), calculateHeight(getDimensionReference(element))));
            element.setTargetVector2D(new Vector2D(calculateX(getVector2DReference(element)), calculateY(getVector2DReference(element))));
        }
    }
}
