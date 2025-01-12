package codes.matheus.pieces;

import codes.matheus.Color;
import codes.matheus.engine.graphics.Sprite;
import codes.matheus.engine.tilemap.TileMap;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public final class Knight extends Piece {
    public Knight(@NotNull Color color, @NotNull TileMap tileMap) {
        super(color, tileMap);
        setSprite(new Sprite((color.equals(Color.WHITE)) ? new File("src/main/resources/pieces/knight-white.png") : new File("src/main/resources/pieces/knight-black.png") ));
    }

    @Override
    public boolean[][] possibleMoves() {
        return new boolean[0][];
    }
}
