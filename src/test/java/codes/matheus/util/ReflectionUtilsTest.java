package codes.matheus.util;

import codes.matheus.engine.ui.GamePanel;
import codes.matheus.engine.ui.Window;
import codes.matheus.pieces.Piece;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReflectionUtilsTest {
    @Test
    void testGetFieldForObject() {
        @NotNull Window window = Window.getInstance("Test");
        @NotNull GamePanel gamePanel = new GamePanel();
        @Nullable Piece piece = ReflectionUtils.getFieldValue(gamePanel, "selectedPiece");

        System.out.println(piece);
        Assertions.assertNull(piece);
    }
}