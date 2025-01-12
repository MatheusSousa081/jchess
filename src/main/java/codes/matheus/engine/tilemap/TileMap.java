package codes.matheus.engine.tilemap;

import codes.matheus.board.Position;
import codes.matheus.engine.core.Dimension;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public final class TileMap {
    private final static int rows = 8;
    private final static int columns = 8;
    private final @NotNull Tile[][] map;

    public TileMap() {
        map = new Tile[8][8];
    }

    public @NotNull Tile getTile(@NotNull Position position) {
        return map[position.toMatrix()[0]][position.toMatrix()[1]];
    }

    public void setTile(@NotNull Position position, @NotNull Tile tile) {
        map[position.toMatrix()[0]][position.toMatrix()[1]] = tile;
    }

    public @NotNull Dimension getSize() {
        return new Dimension(columns * map[0][0].getDimension().getWidth(), rows * map[0][0].getDimension().getHeight());
    }

    public boolean isValidPosition(@NotNull Position position) {
        return position.toMatrix()[0] >= 0 && position.toMatrix()[0] < rows && position.toMatrix()[1] >= 0 && position.toMatrix()[1] < columns;
    }

    public void draw(@NotNull Graphics2D graphics2D, boolean[][] possibleMoves) {
        for (int i = 8; i >= 1; i--) {
            for (int j = 0; j < 8; j++) {
                @NotNull Position position = new Position((char) ('a' + j), i);
                @NotNull Tile tile = map[position.toMatrix()[0]][position.toMatrix()[1]];
                tile.draw(graphics2D);
            }
        }
    }
}
