package itsu.java.musicplayerfx.listener.action;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXDialogLayout;
import itsu.java.musicplayerfx.GUIManager;
import itsu.java.musicplayerfx.SongPlayerController;
import itsu.java.musicplayerfx.listener.change.TweetWebViewChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.util.Map;


public class TweetButtonActionListener implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent event) {
        if (SongPlayerController.isPlaying()) {
            final Stage stage = new Stage();
            final JFXAlert<Void> alert = new JFXAlert<>();
            final WebView view = new WebView();
            final Scene scene;
            final JFXDialogLayout layout = new JFXDialogLayout();
            Map<String, Object> data = SongPlayerController.getSongData();

            String tweetData = "";

            if (data.containsKey("title") && data.containsKey("album") && data.containsKey("artist"))
                tweetData = data.get("title") + " / " + data.get("album") + " - " + data.get("artist");

            else if (data.containsKey("title") && data.containsKey("artist"))
                tweetData = data.get("title") + " / " + data.get("artist");

            else if (data.containsKey("title"))
                tweetData = String.valueOf(data.get("title"));

            else return;

            view.getEngine().setJavaScriptEnabled(true);
            view.getEngine().load("https://twitter.com/intent/tweet?text=" + tweetData + "&hashtags=Nowplaying");
            view.setPrefSize(500, 300);
            view.getEngine().getLoadWorker().stateProperty().addListener(new TweetWebViewChangeListener(stage, view));

            JFXDecorator decorator = new JFXDecorator(stage, new StackPane(view));
            decorator.setCustomMaximize(true);

            scene = new Scene(decorator, 500, 300);
            scene.getStylesheets().add(GUIManager.class.getClassLoader().getResource("css/TweetWindow.css").toExternalForm());

            stage.setScene(scene);
            stage.setMaximized(false);
            stage.setResizable(false);
            stage.setTitle("MusicPlayerFX - Tweet");
            stage.setMaxWidth(500);
            stage.setMaxHeight(300);
            stage.show();
        }
    }

}
