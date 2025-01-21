package codes.matheus.game;

import codes.matheus.Color;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class GameManager {
    private volatile @NotNull GameState gameState = GameState.PROGRESS;
    private @Nullable Color currentPlayer;
    private int turn = 0;

    public GameManager() {
        currentPlayer = Color.WHITE;
    }

    public @NotNull GameState getGameState() {
        return gameState;
    }

    public void setGameState(@NotNull GameState gameState) {
        this.gameState = gameState;
    }

    public @Nullable Color getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(@Nullable Color currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void nextTurn() {
        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
        turn++;
    }

    public @NotNull Color getOpponent(@NotNull Color color) {
        return (color.equals(Color.WHITE)) ? Color.BLACK : Color.WHITE;
    }

    public enum GameState {
        PROGRESS,
        CHECK,
        CHECKMATE,
        DRAW
    }
}
