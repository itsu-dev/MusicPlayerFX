package itsu.java.musicplayerfx.listener.change;

import itsu.java.musicplayerfx.Utils.DialogFactory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class TweetWebViewChangeListener implements ChangeListener<Worker.State> {

    private Stage stage;
    private WebView view;

    public TweetWebViewChangeListener(Stage stage, WebView view) {
        this.stage = stage;
        this.view = view;
    }

    @Override
    public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) {
        if (view.getEngine().getLocation().startsWith("https://twitter.com/intent/tweet/complete")) {
            stage.close();
            DialogFactory.showDialog(null, "ツイートしました。");
        }
    }

}
