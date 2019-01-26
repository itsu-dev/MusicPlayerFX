package itsu.java.musicplayerfx;

import com.jfoenix.controls.*;
import itsu.java.musicplayerfx.Utils.FormatUtil;
import itsu.java.musicplayerfx.components.AppBar;
import itsu.java.musicplayerfx.components.DrawerContent;
import itsu.java.musicplayerfx.components.NavigationDrawer;
import itsu.java.musicplayerfx.components.PlayingDisplay;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Map;

public class GUIManager {

    private static NavigationDrawer drawer;
    private static AppBar appBar;
    private static BorderPane root;
    private static StackPane pane;
    private static Pane display;

    public static Scene initGUI(Stage stage) {
        root = new BorderPane();
        pane = new StackPane();

        drawer = new NavigationDrawer();
        appBar = new AppBar();

        root.setTop(appBar);
        root.setCenter(new PlayingDisplay());
        root.setLeft(drawer.getDrawersStack());
        root.setStyle("-fx-background-color: transparent;");

        //pane.getChildren().add(display = new PlayingDisplay());
        //pane.getChildren().add(root);
        //pane.setStyle("-fx-background-color: transparent;");

        JFXDecorator decorator = new JFXDecorator(stage, root);
        decorator.setCustomMaximize(true);

        Scene scene = new Scene(decorator, 1000, 800);
        scene.getStylesheets().add(GUIManager.class.getClassLoader().getResource("css/OriginalDecorator.css").toExternalForm());

        return scene;
    }

    public static void setDisplay(Pane node) {
        if (root.getCenter().getId().equals(node.getId())) return;
        root.setCenter(node);
    }

    public static void ready() {
        getDrawerContent().createSpectrum();

        Map<String, Object> data = SongPlayerController.getSongData();
        getDrawerContent().setSongName(String.valueOf(data.get("title")));
        getDrawerContent().setAlbumName(String.valueOf(data.get("artist")) + " / " + String.valueOf(data.get("album")));
        getDrawerContent().setAlbumImage(SongPlayerController.getAlbumArtWork());
        getDrawerContent().setMaxTime(SongPlayerController.getPlayer().getTotalDuration().toSeconds());
        getDrawerContent().setMaxTimeLabel(FormatUtil.secToString(SongPlayerController.getPlayer().getTotalDuration().toSeconds()));
        getDrawerContent().toggleStartStop(true);

        if (root.getCenter() instanceof PlayingDisplay) {
            PlayingDisplay d = ((PlayingDisplay) root.getCenter());
            d.setPlayingData();
            d.setImage(SongPlayerController.getAlbumArtWork());
            d.setMaxTime(SongPlayerController.getPlayer().getTotalDuration().toSeconds());
            d.setTimeLabel("0:00/" + FormatUtil.secToString(SongPlayerController.getPlayer().getTotalDuration().toSeconds()));
        }
    }

    public static void update() {
        GUIManager.getDrawerContent().setTime(SongPlayerController.getPlayer().getCurrentTime().toSeconds());
        GUIManager.getDrawerContent().setTimeLabel(FormatUtil.secToString((SongPlayerController.getPlayer().getCurrentTime().toSeconds())));

        if (root.getCenter() instanceof PlayingDisplay) {
            PlayingDisplay d = ((PlayingDisplay) root.getCenter());
            d.setTimeLabel(FormatUtil.secToString(SongPlayerController.getPlayer().getCurrentTime().toSeconds()));
            d.setTime(SongPlayerController.getPlayer().getCurrentTime().toSeconds());
        }
    }

    public static StackPane getRootPane() {
        return pane;
    }

    public static NavigationDrawer getDrawer() {
        return drawer;
    }

    public static AppBar getAppBar() {
        return appBar;
    }

    public static DrawerContent getDrawerContent() {
        return drawer.getDrawerContent();
    }
}
