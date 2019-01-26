package itsu.java.musicplayerfx.listener.change;

import itsu.java.musicplayerfx.SongPlayerController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class VolumeBarChangeListener implements ChangeListener<Number> {

    @Override
    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        SongPlayerController.volume(newValue.doubleValue());
    }

}
