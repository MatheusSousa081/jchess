package codes.matheus.engine.graphics;

import codes.matheus.engine.core.Dimension;
import codes.matheus.engine.core.Vector2D;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.awt.*;

public abstract class Element {
    private @NotNull Vector2D vector2D;
    private @NotNull Vector2D targetVector2D;
    private @NotNull Dimension dimension;

    public Element(@NotNull Vector2D vector2D, @NotNull Dimension dimension) {
        this.vector2D = vector2D;
        this.targetVector2D = vector2D;
        this.dimension = dimension;
        Elements.getElements().add(this);
    }

    public @NotNull Vector2D getVector2D() {
        return vector2D;
    }

    public void setVector2D(@NotNull Vector2D vector2D) {
        this.vector2D = vector2D;
    }

    public @NotNull Vector2D getTargetVector2D() {
        return targetVector2D;
    }

    public void setTargetVector2D(@NotNull Vector2D targetVector2D) {
        this.targetVector2D = targetVector2D;
    }

    public @NotNull Dimension getDimension() {
        return dimension;
    }

    public void setDimension(@NotNull Dimension dimension) {
        this.dimension = dimension;
    }

    public abstract void draw(@NotNull Graphics2D graphics2D);

    public void update(@Range(from = 0, to =  1) double lerpFactor) {
        setVector2D(vector2D.lerp(getTargetVector2D(), lerpFactor));
    }

    public boolean intersect(@NotNull Vector2D vector2D) {
        return vector2D.getX() >= getVector2D().getX() &&
                vector2D.getX() <= getVector2D().getX() + getDimension().getWidth() &&
                vector2D.getY() >= getVector2D().getY() &&
                vector2D.getY() <= getVector2D().getY() + getDimension().getHeight();
    }
}
