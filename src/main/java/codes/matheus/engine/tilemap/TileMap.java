package codes.matheus.engine.tilemap;

import codes.matheus.board.MoveExecutor;
import codes.matheus.board.Position;
import codes.matheus.engine.core.Dimension;
import codes.matheus.engine.graphics.Sprite;
import codes.matheus.game.Game;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public final class TileMap {
    private static @NotNull Sprite brownDark = Game.getTheme().getDark();
    private static @NotNull Sprite brownLight = Game.getTheme().getLight();
    private static @NotNull Sprite brownDarkMove = Game.getTheme().getDarkMove();
    private static @NotNull Sprite brownLightMove = Game.getTheme().getLightMove();
    private final static int rows = 8;
    private final static int columns = 8;

    public static void updateTheme() {
        brownDark = Game.getTheme().getDark();
        brownLight = Game.getTheme().getLight();
        brownDarkMove = Game.getTheme().getDarkMove();
        brownLightMove = Game.getTheme().getLightMove();
    }

    private final @NotNull Tile[][] map;
    private @NotNull MoveExecutor moveExecutor;

    public TileMap() {
        map = new Tile[8][8];
    }

    public @NotNull Tile getTile(@NotNull Position position) {
        return map[position.getRowMatrix()][position.getColumnMatrix()];
    }

    public void setTile(@NotNull Position position, @NotNull Tile tile) {
        map[position.getRowMatrix()][position.getColumnMatrix()] = tile;
    }

    public @NotNull MoveExecutor getMoveExecutor() {
        return moveExecutor;
    }

    public void setMoveExecutor(@NotNull MoveExecutor moveExecutor) {
        this.moveExecutor = moveExecutor;
    }

    public @NotNull Dimension getSize() {
        return new Dimension(columns * map[0][0].getDimension().getWidth(), rows * map[0][0].getDimension().getHeight());
    }

    public boolean isValidPosition(@NotNull Position position) {
        return position.getRowMatrix() >= 0 && position.getRowMatrix() < rows && position.getColumnMatrix() >= 0 && position.getColumnMatrix() < columns;
    }

    public void draw(@NotNull Graphics2D graphics2D, boolean[][] possibleMoves) {
        for (int i = 8; i >= 1; i--) {
            for (int j = 0; j < 8; j++) {
                @NotNull Position position = new Position((char) ('a' + j), i);
                @NotNull Tile tile = map[position.getRowMatrix()][position.getColumnMatrix()];
                if (possibleMoves[position.getRowMatrix()][position.getColumnMatrix()]) {
                    tile.setBackground((i + j) % 2 == 0 ? brownDarkMove : brownLightMove);
                } else {
                    tile.setBackground((i + j) % 2 == 0 ? brownDark : brownLight);
                }
                tile.draw(graphics2D);
            }
        }
    }
}
