package codes.matheus.game;

import org.jetbrains.annotations.NotNull;

public final class GameManager {
    private volatile @NotNull GameState gameState = GameState.PROGRESS;
    private int turn = 0;

    public GameManager() {
    }

    public @NotNull GameState getGameState() {
        return gameState;
    }

    public void setGameState(@NotNull GameState gameState) {
        this.gameState = gameState;
    }

    public void nextTurn() {
        turn++;
    }

    public enum GameState {
        PROGRESS,
        CHECK,
        CHECKMATE,
        DRAW,
        FINISHED
    }
}
