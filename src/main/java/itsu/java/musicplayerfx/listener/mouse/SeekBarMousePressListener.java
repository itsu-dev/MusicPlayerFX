package itsu.java.musicplayerfx.listener.mouse;

import itsu.java.musicplayerfx.SongPlayerController;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class SeekBarMousePressListener implements EventHandler<MouseEvent> {

    @Override
    public void handle(MouseEvent event) {
        SongPlayerController.pause();
    }

}
