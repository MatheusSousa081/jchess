package codes.matheus.engine.ui;

import codes.matheus.engine.core.Dimension;
import codes.matheus.engine.core.Vector2D;
import codes.matheus.engine.graphics.Button;
import codes.matheus.engine.graphics.Elements;
import codes.matheus.engine.util.Fps;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class SettingsPanel extends ResponsivePanel {
    private final @NotNull MouseInput mouseInput = new MouseInput();
    private final @NotNull Button button = new Button(new Vector2D(100, 650), new Dimension(200, 32), "Config", null, new Color(129, 182, 76));

    public SettingsPanel() {
        button.setEvent(btn -> System.out.println("Olá"));
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
        button.draw(graphics2D);

    }

    private final class MouseInput implements MouseListener, MouseMotionListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            button.handleClick(new Vector2D(e.getX(), e.getY()));
        }

        @Override
        public void mousePressed(MouseEvent e) {

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

        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }
    }
}
