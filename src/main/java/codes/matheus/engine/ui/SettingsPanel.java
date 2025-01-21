package codes.matheus.engine.ui;

import codes.matheus.engine.core.Dimension;
import codes.matheus.engine.core.Vector2D;
import codes.matheus.engine.graphics.Button;
import codes.matheus.engine.graphics.Elements;
import codes.matheus.engine.util.Fps;
import codes.matheus.game.Game;
import codes.matheus.player.Player;
import codes.matheus.player.Username;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class SettingsPanel extends ResponsivePanel {
    private final @NotNull MouseInput mouseInput = new MouseInput();
    private final @NotNull Game game;
    private final @NotNull UI ui;
    private final @NotNull ConcurrentHashMap<@NotNull String, @NotNull Button> buttons = new ConcurrentHashMap<>();

    public SettingsPanel(@NotNull Game game) {
        this.game = game;
        this.ui = new UI();
    }

    @Override
    public void addNotify() {
        super.addNotify();
        setBackground(Color.GRAY);
        addMouseListener(mouseInput);
        addMouseMotionListener(mouseInput);
        getResponsiveLayout().addAllReferences(Elements.getElements());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        @NotNull Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.drawString("Fps: " + Fps.getFps(), 1, 10);
        ui.draw(graphics2D);
    }

    private final class UI {
        public UI() {
            addButton(new Button(new Vector2D(200, 300), new Dimension(175, 50), "Configurations", "Sign in",
                    null, new Color(129, 182, 76)), btn -> addUser());

            addButton(new Button(new Vector2D(200, 375), new Dimension(175, 50), "Play", "Play",
                    null, new Color(129, 182, 76)), btn -> start());
        }

        private synchronized void addButton(@NotNull Button button, @NotNull Consumer<@NotNull Button> event) {
            button.setEvent(event);
            buttons.put(button.getDescription(), button);
        }

        private synchronized void removeButton(@NotNull String description) {
            buttons.remove(description);
        }

        private void start() {
            if (game.getPlayer() == null) {
                JOptionPane.showMessageDialog(null, "Please add a player before starting the game!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int randomChoice = (int) (Math.random() * 2) + 1;
            if (randomChoice == 1) {
                game.getPlayer().setColor(codes.matheus.Color.WHITE);
                JOptionPane.showMessageDialog(null, "Player starts as White!", "Game Start", JOptionPane.INFORMATION_MESSAGE);
            } else {
                game.getPlayer().setColor(codes.matheus.Color.BLACK);
                JOptionPane.showMessageDialog(null, "Player starts as Black! Waiting for opponent's turn.", "Game Start", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        private void addUser() {
            if (game.getPlayer() == null) {
                @NotNull String username = JOptionPane.showInputDialog(null, "Enter the username:", "Add user", JOptionPane.PLAIN_MESSAGE);
                while (!Username.isValid(username)) {
                    username = JOptionPane.showInputDialog(null, "Enter the username:", "Add user", JOptionPane.PLAIN_MESSAGE);
                }
                game.setPlayer(new Player(Username.parse(username)));
            }

            removeButton("Configurations");
        }

        private void draw(@NotNull Graphics2D graphics2D) {
            buttons.values().forEach(btn -> btn.draw(graphics2D));
        }
    }

    private final class MouseInput implements MouseListener, MouseMotionListener {
        private @NotNull Vector2D mouseVector2D;

        @Override
        public void mouseClicked(MouseEvent e) {
            buttons.values().forEach(btn -> btn.handleClick(mouseVector2D));
        }

        @Override
        public void mousePressed(MouseEvent e) {
            mouseVector2D = new Vector2D(e.getX(), e.getY());
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseDragged(MouseEvent e) {
            mouseVector2D = new Vector2D(e.getX(), e.getY());
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            mouseVector2D = new Vector2D(e.getX(), e.getY());
        }
    }
}
