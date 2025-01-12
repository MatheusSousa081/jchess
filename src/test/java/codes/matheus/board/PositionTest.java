package codes.matheus.board;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {
    @Test
    void testWindowPosition() {
        @NotNull Position position = new Position('a', 8);
        Assertions.assertEquals("0, 0", position.toVector2D(64).toString());
    }

    @Test
    void testAllPositions() {
        @NotNull Position position = new Position('a', 1);
        @NotNull StringBuilder builder = new StringBuilder();
        for (int i = 8; i >= 1; i--) {
            for (int j = 0; j < 8; j++) {
                position.move((char) ('a' + j), i);
                builder.append(position).append(" ");
            }
            if (i != 1) {
                builder.append("\n");
            }
        }

        assertEquals("""
                        a8 b8 c8 d8 e8 f8 g8 h8\s
                        a7 b7 c7 d7 e7 f7 g7 h7\s
                        a6 b6 c6 d6 e6 f6 g6 h6\s
                        a5 b5 c5 d5 e5 f5 g5 h5\s
                        a4 b4 c4 d4 e4 f4 g4 h4\s
                        a3 b3 c3 d3 e3 f3 g3 h3\s
                        a2 b2 c2 d2 e2 f2 g2 h2\s
                        a1 b1 c1 d1 e1 f1 g1 h1\s""",
                builder.toString());
    }
}