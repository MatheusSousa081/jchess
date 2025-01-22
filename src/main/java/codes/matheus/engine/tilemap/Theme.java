package codes.matheus.engine.tilemap;

import codes.matheus.engine.graphics.Sprite;
import codes.matheus.util.ResourceManager;
import org.jetbrains.annotations.NotNull;

public enum Theme {
    CLASSIC(ResourceManager.getSprite("brown-light"), ResourceManager.getSprite("brown-dark"), ResourceManager.getSprite("brown-light-move"), ResourceManager.getSprite("brown-dark-move")),
    OCEAN(ResourceManager.getSprite("cyan-light"), ResourceManager.getSprite("cyan-dark"), ResourceManager.getSprite("cyan-light-move"), ResourceManager.getSprite("cyan-dark-move")),
    GLASS(ResourceManager.getSprite("glass-light"), ResourceManager.getSprite("glass-dark"), ResourceManager.getSprite("glass-light-move"), ResourceManager.getSprite("glass-dark-move"));

    private final @NotNull Sprite light;
    private final @NotNull Sprite dark;
    private final @NotNull Sprite lightMove;
    private final @NotNull Sprite darkMove;

    Theme(@NotNull Sprite light, @NotNull Sprite dark, @NotNull Sprite lightMove, @NotNull Sprite darkMove) {
        this.light = light;
        this.dark = dark;
        this.lightMove = lightMove;
        this.darkMove = darkMove;
    }

    public @NotNull Sprite getLight() {
        return light;
    }

    public @NotNull Sprite getDark() {
        return dark;
    }

    public @NotNull Sprite getLightMove() {
        return lightMove;
    }

    public @NotNull Sprite getDarkMove() {
        return darkMove;
    }
}
