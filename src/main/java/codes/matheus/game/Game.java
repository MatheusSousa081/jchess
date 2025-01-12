package codes.matheus.game;

import codes.matheus.board.Move;
import codes.matheus.board.MoveExecutor;
import codes.matheus.board.Position;
import codes.matheus.engine.core.Core;
import codes.matheus.engine.core.Dimension;
import codes.matheus.engine.graphics.Element;
import codes.matheus.engine.graphics.Elements;
import codes.matheus.engine.ui.GamePanel;
import codes.matheus.engine.ui.SettingsPanel;
import codes.matheus.engine.ui.Window;
import codes.matheus.engine.util.Fps;
import codes.matheus.pieces.Piece;
import codes.matheus.util.ReflectionUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public final class Game implements Runnable, Core {
    private final @NotNull Window window = Window.getInstance("Chess", Dimension.Resolutions.HD.getDimension());
    private final @NotNull SettingsPanel settingsPanel = new SettingsPanel();
    private final @NotNull GameManager manager = new GameManager();
    private final @NotNull GamePanel panel = new GamePanel();
    private final @NotNull MoveExecutor moveExecutor = new MoveExecutor(panel.getPieces(), panel.getTileMap(), manager);
    private volatile boolean waitingForMove = true;

    public Game() {
        window.setMinimumSize(new java.awt.Dimension(750, 650));
        window.setLayout(new BorderLayout());
        panel.setPreferredSize(new java.awt.Dimension((int) (window.getWidth() * 0.5), window.getHeight()));
        settingsPanel.setPreferredSize(new java.awt.Dimension((int) (window.getWidth() * 0.5), window.getHeight()));
        window.add(panel, BorderLayout.WEST);
        window.add(settingsPanel, BorderLayout.CENTER);

        window.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                panel.setPreferredSize(new java.awt.Dimension((int) (window.getWidth() * 0.5), window.getHeight()));
                settingsPanel.setPreferredSize(new java.awt.Dimension((int) (window.getWidth() * 0.5), window.getHeight()));
                window.repaint();
                window.revalidate();
            }
        });
    }

    @Override
    public void run() {
        manager.setGameState(GameManager.GameState.PROGRESS);
        while (manager.getGameState() != GameManager.GameState.FINISHED) {
            Fps.update(this);
        }
    }

    @Override
    public void update(double delta) {
        if (waitingForMove) {
            processPlayerTurn();
        } else {
            Elements.getElements().forEach(element -> element.update(1));
            waitingForMove = true;
        }
    }

    @Override
    public void render() {
        panel.repaint();
        panel.revalidate();
        settingsPanel.repaint();
        settingsPanel.revalidate();
    }

    private void processPlayerTurn() {
        @Nullable Position origin = ReflectionUtils.getFieldValue(panel, "originPosition");
        @Nullable Position target = ReflectionUtils.getFieldValue(panel, "targetPosition");
        @Nullable Piece piece = ReflectionUtils.getFieldValue(panel, "selectedPiece");

        if (piece != null && origin != null && target != null) {
            @NotNull Move move = new Move(origin, target, piece);
            moveExecutor.execute(move);
            ReflectionUtils.setFieldValue(panel, "originPosition", null);
            ReflectionUtils.setFieldValue(panel, "targetPosition", null);
            ReflectionUtils.setFieldValue(panel, "selectedPiece", null);
        }
        waitingForMove = false;
    }
}