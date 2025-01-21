package codes.matheus.game;

import codes.matheus.Color;
import codes.matheus.board.Move;
import codes.matheus.board.MoveExecutor;
import codes.matheus.board.Position;
import codes.matheus.engine.core.Core;
import codes.matheus.engine.core.Dimension;
import codes.matheus.engine.graphics.Elements;
import codes.matheus.engine.ui.GamePanel;
import codes.matheus.engine.ui.SettingsPanel;
import codes.matheus.engine.ui.Window;
import codes.matheus.engine.util.Fps;
import codes.matheus.pieces.Piece;
import codes.matheus.player.Player;
import codes.matheus.util.ReflectionUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public final class Game implements Runnable, Core {
    private final @NotNull Window window = Window.getInstance("Chess", Dimension.Resolutions.HD.getDimension());
    private final @NotNull SettingsPanel settingsPanel = new SettingsPanel(this);
    private final @NotNull GameManager manager = new GameManager();
    private final @NotNull GamePanel panel = new GamePanel();
    private final @NotNull MoveExecutor moveExecutor = new MoveExecutor(panel.getPieces(), panel.getTileMap(), manager);
    private @Nullable Player player = null;
    private volatile boolean running = false;

    public Game() {
        window.setMinimumSize(new java.awt.Dimension(750, 650));
        window.setLayout(new BorderLayout());
        panel.setPreferredSize(new java.awt.Dimension((int) (window.getWidth() * 0.6), window.getHeight()));
        settingsPanel.setPreferredSize(new java.awt.Dimension((int) (window.getWidth() * 0.4), window.getHeight()));
        window.add(panel, BorderLayout.CENTER);
        window.add(settingsPanel, BorderLayout.EAST);
        window.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                panel.setPreferredSize(new java.awt.Dimension((int) (window.getWidth() * 0.6), window.getHeight()));
                settingsPanel.setPreferredSize(new java.awt.Dimension((int) (window.getWidth() * 0.4), window.getHeight()));
                window.repaint();
                window.revalidate();
            }
        });
    }

    public @Nullable Player getPlayer() {
        return player;
    }

    public void setPlayer(@Nullable Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        running = true;
        panel.getTileMap().setMoveExecutor(moveExecutor);
        manager.setGameState(GameManager.GameState.PROGRESS);
        while (running) {
            Fps.update(this);
        }
    }

    @Override
    public void update(double delta) {
        processPlayerTurn();
        Elements.getElements().forEach(element -> element.update(1));
    }

    @Override
    public void render() {
        panel.repaint();
        panel.revalidate();
        settingsPanel.repaint();
        settingsPanel.revalidate();
    }

    private void processPlayerTurn() {
        if (manager.getGameState() == GameManager.GameState.CHECKMATE || manager.getGameState() == GameManager.GameState.DRAW || player == null) {
            resetMoves();
            return;
        }

        panel.setPlayer(player);
        panel.getPlayer2().setColor((player.getColor() == Color.WHITE) ? Color.BLACK : Color.WHITE);
        @Nullable Position origin = ReflectionUtils.getFieldValue(panel, "originPosition");
        @Nullable Position target = ReflectionUtils.getFieldValue(panel, "targetPosition");
        @Nullable Piece piece = ReflectionUtils.getFieldValue(panel, "selectedPiece");

        if (piece != null && piece.getColor() != manager.getCurrentPlayer()) {
            resetMoves();
            return;
        }

        if (piece != null && origin != null && target != null) {
            if (piece.possibleMoves()[target.getRowMatrix()][target.getColumnMatrix()]) {
                @NotNull Move move = new Move(origin, target, piece);
                moveExecutor.execute(move);
                manager.nextTurn();
            }
            resetMoves();
        }
    }

    private void resetMoves() {
        ReflectionUtils.setFieldValue(panel, "originPosition", null);
        ReflectionUtils.setFieldValue(panel, "targetPosition", null);
        ReflectionUtils.setFieldValue(panel, "selectedPiece", null);
    }
}
