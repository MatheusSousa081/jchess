package codes.matheus.pieces;

import codes.matheus.Color;
import codes.matheus.engine.graphics.Sprite;
import codes.matheus.engine.tilemap.TileMap;
import org.jetbrains.annotations.NotNull;

import java.io.File;


public final class Bishop extends Piece {
    public Bishop(@NotNull Color color, @NotNull TileMap tileMap) {
        super(color, tileMap);
        setSprite(new Sprite((color.equals(Color.WHITE)) ? new File("src/main/resources/pieces/bishop-white.png") : new File("src/main/resources/pieces/bishop-black.png") ));
    }

    @Override
    public boolean[][] possibleMoves() {
        return new boolean[0][];
    }
}
