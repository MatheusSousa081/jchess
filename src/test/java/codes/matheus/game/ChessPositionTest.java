package codes.matheus.game;

import codes.matheus.board.Board;
import codes.matheus.board.ChessPosition;
import codes.matheus.board.Position;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class ChessPositionTest {
    @Test
    void tesChessPosition() {
        @NotNull Position position = new Position(7, 0);
        @NotNull ChessPosition chessPosition = ChessPosition.from(position);
        Assertions.assertEquals("a1", chessPosition.toString(), "Cast done successfully");
    }

    @Test
    void allChessPosition() {
        @NotNull List<@NotNull ChessPosition> allPositions = new ArrayList<>();
        for (int i = 0; i < Board.ROWS; i++) {
            for (int j = 0; j < Board.COLUMNS; j++) {
                allPositions.add(ChessPosition.from(new Position(i, j)));
            }
        }

        System.out.println(allPositions);
    }
}