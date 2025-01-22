package codes.matheus.util;

import codes.matheus.engine.graphics.Sprite;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ConcurrentHashMap;

public final class ResourceManager {
    private ResourceManager() {
        throw new UnsupportedOperationException();
    }

    private static final @NotNull ConcurrentHashMap<@NotNull String, @NotNull Sprite> images = new ConcurrentHashMap<>();

    static {
        images.put("brown-dark", new Sprite("/background/brown-dark.png"));
        images.put("brown-light", new Sprite("/background/brown-light.png"));
        images.put("brown-dark-move", new Sprite("/background/brown-dark-move.png"));
        images.put("brown-light-move", new Sprite("/background/brown-light-move.png"));
        images.put("cyan-dark", new Sprite("/background/cyan-dark.png"));
        images.put("cyan-light", new Sprite("/background/cyan-light.png"));
        images.put("cyan-dark-move", new Sprite("/background/cyan-dark-move.png"));
        images.put("cyan-light-move", new Sprite("/background/cyan-light-move.png"));
        images.put("glass-dark", new Sprite("/background/glass-dark.png"));
        images.put("glass-light", new Sprite("/background/glass-light.png"));
        images.put("glass-dark-move", new Sprite("/background/glass-dark-move.png"));
        images.put("glass-light-move", new Sprite("/background/glass-light-move.png"));
        images.put("bishop-black", new Sprite("/pieces/bishop-black.png"));
        images.put("bishop-white", new Sprite("/pieces/bishop-white.png"));
        images.put("king-black", new Sprite("/pieces/king-black.png"));
        images.put("king-white", new Sprite("/pieces/king-white.png"));
        images.put("knight-black", new Sprite("/pieces/knight-black.png"));
        images.put("knight-white", new Sprite("/pieces/knight-white.png"));
        images.put("pawn-black", new Sprite("/pieces/pawn-black.png"));
        images.put("pawn-white", new Sprite("/pieces/pawn-white.png"));
        images.put("queen-black", new Sprite("/pieces/queen-black.png"));
        images.put("queen-white", new Sprite("/pieces/queen-white.png"));
        images.put("rook-black", new Sprite("/pieces/rook-black.png"));
        images.put("rook-white", new Sprite("/pieces/rook-white.png"));
        images.put("configuration", new Sprite("/ui/configuration.png"));
    }

    public static @NotNull Sprite getSprite(@NotNull String fileName) {
        return images.get(fileName);
    }
}