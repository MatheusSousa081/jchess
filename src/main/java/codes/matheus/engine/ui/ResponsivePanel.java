package codes.matheus.engine.ui;

import codes.matheus.engine.graphics.Elements;
import codes.matheus.engine.layout.ResponsiveLayout;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ResponsivePanel extends JPanel {
    private final @NotNull ResponsiveLayout layout = new ResponsiveLayout(this);

    public ResponsivePanel() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                layout.resizeComponents(Elements.getElements());
                revalidate();
                repaint();
            }
        });
    }

    public @NotNull ResponsiveLayout getResponsiveLayout() {
        return layout;
    }
}