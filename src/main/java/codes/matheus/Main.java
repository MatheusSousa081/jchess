package codes.matheus;

import codes.matheus.game.Game;
import org.jetbrains.annotations.NotNull;

public class Main {
    public static void main(String[] args) {
        @NotNull Game game = new Game();
        game.run();
    }
}