package itsu.java.musicplayerfx;

import com.jfoenix.controls.*;
import itsu.java.musicplayerfx.components.AppBar;
import itsu.java.musicplayerfx.components.DrawerContent;
import itsu.java.musicplayerfx.components.NavigationDrawer;
import itsu.java.musicplayerfx.components.PlayingDisplay;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

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
        root.setLeft(drawer.getDrawersStack());
        root.setStyle("-fx-background-color: transparent;");

        pane.getChildren().add(display = new PlayingDisplay());
        pane.getChildren().add(root);
        pane.setStyle("-fx-background-color: transparent;");

        JFXDecorator decorator = new JFXDecorator(stage, pane);
        decorator.setCustomMaximize(true);

        Scene scene = new Scene(decorator, 1000, 800);
        scene.getStylesheets().add(GUIManager.class.getClassLoader().getResource("OriginalDecorator.css").toExternalForm());

        return scene;
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

    public static void setDisplay(Pane node) {
        if (display != null && display.getId().equals(node.getId())) return;

        display = node;

        pane.getChildren().add(node);
        pane.getChildren().remove(root);
        pane.getChildren().add(root);
    }

    public static void play() {
        getDrawerContent().createSpectrum();
        getDrawerContent().setAlbumImage(SongPlayerController.getAlbumArkWork());
        if (display instanceof PlayingDisplay) {
            PlayingDisplay d = ((PlayingDisplay) display);
            d.setPlayingData();
            d.setImage(SongPlayerController.getAlbumArkWork());
            d.setMaxTime(SongPlayerController.getPlayer().getTotalDuration().toSeconds());
            d.setTimeLabel("0:00/" + SongPlayerController.secToString(SongPlayerController.getPlayer().getTotalDuration().toSeconds()));
        }
    }

    public static void update() {
        GUIManager.getDrawerContent().setTime(SongPlayerController.getPlayer().getCurrentTime().toSeconds());
        GUIManager.getDrawerContent().setTimeLabel(SongPlayerController.secToString((SongPlayerController.getPlayer().getCurrentTime().toSeconds())));

        if (display instanceof PlayingDisplay) {
            PlayingDisplay d = ((PlayingDisplay) display);
            d.setTimeLabel(
                    SongPlayerController.secToString(SongPlayerController.getPlayer().getCurrentTime().toSeconds())
            );
            d.setTime(SongPlayerController.getPlayer().getCurrentTime().toSeconds());
        }
    }

}
