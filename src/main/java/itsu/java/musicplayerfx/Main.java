package itsu.java.musicplayerfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        SongPlayerController.init();

        Scene scene = GUIManager.initGUI(primaryStage);

        SongPlayerController.setPlayMusic(new File("C:\\Users\\itsu\\Music\\その他\\春雷.mp3"));
        SongPlayerController.play();

        primaryStage.setTitle("MusicPlayerFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
