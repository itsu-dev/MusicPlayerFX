package itsu.java.musicplayerfx;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        SongPlayerController.init();

        Scene scene = GUIManager.initGUI(primaryStage);

        SongPlayerController.setPlayMusic(new File("C:\\Users\\itsu\\Music\\SEKAI NO OWARI\\炎と森のカーニバル.mp3"));
        SongPlayerController.play();

        primaryStage.setTitle("MusicPlayerFX");
        primaryStage.setScene(scene);
        primaryStage.setHeight(850);
        primaryStage.setMinWidth(300);
        primaryStage.setMinHeight(845);
        primaryStage.getIcons().add(new Image(this.getClass().getClassLoader().getResource("icons/icon.png").toExternalForm()));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
