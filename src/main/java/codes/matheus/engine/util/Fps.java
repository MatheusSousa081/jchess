package codes.matheus.engine.util;

import codes.matheus.engine.core.Core;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public final class Fps {
    public static int TARGET_FPS = 60;
    public static int TARGET_UPS = 60;
    private static int Fps = 0;

    private Fps() {
        throw new UnsupportedOperationException();
    }

    public static void update(@NotNull Core core) {
        double timePerFrame = 1000000000.0 / TARGET_FPS;
        double timePerUpdate = 1000000000.0 / TARGET_UPS;

        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        while (true) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                core.update(deltaU);
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                try {
                    SwingUtilities.invokeAndWait(core::render);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                Fps = frames;
                frames = 0;
                updates = 0;
            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static int getFps() {
        return Fps;
    }
}
