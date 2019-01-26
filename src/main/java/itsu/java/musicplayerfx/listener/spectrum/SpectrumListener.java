package itsu.java.musicplayerfx.listener.spectrum;

import itsu.java.musicplayerfx.SongPlayerController;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.paint.Color;

public class SpectrumListener implements AudioSpectrumListener {

    public static final double LINE_WIDTH = 268 / 128.0;

    private Canvas canvas;
    private AudioSpectrumListener beforeListener;

    public SpectrumListener(Canvas canvas) {
        this.canvas = canvas;
        beforeListener = SongPlayerController.getPlayer().getAudioSpectrumListener();
    }

    @Override
    public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {
        if (beforeListener != null) {
            beforeListener.spectrumDataUpdate(timestamp, duration, magnitudes, phases);
        }

        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setFill(Color.web("#161616"));
        g.fillRect(0.0, 0.0, canvas.getWidth(), canvas.getHeight());

        for (int i = 0; i < phases.length; i++) {
            double hue = (phases[i] + Math.PI) / (2.0 * Math.PI);
            if (hue < 0.0) {
                hue = 0.0;
            }
            if (hue > 1.0) {
                hue = 1.0;
            }

            g.setFill(Color.web("#ffffff"));
            g.fillRect(i * LINE_WIDTH, canvas.getHeight() - (magnitudes[i] + 60.0), LINE_WIDTH, canvas.getHeight());
        }
    }

}
