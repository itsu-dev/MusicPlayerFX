package itsu.java.musicplayerfx.components;

import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXSlider;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;
import itsu.java.musicplayerfx.GUIManager;
import itsu.java.musicplayerfx.SongPlayerController;
import itsu.java.musicplayerfx.Utils.FormatUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableMap;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.Map;


public class PlayingDisplay extends StackPane {

    private BorderPane borderPane;
    private ImageView view;

    private JFXProgressBar bar;
    private JFXSlider seekBar;
    private JFXSlider volume;
    private Text time;
    private Text title;

    private Button back;
    private Button startStop;
    private Button go;

    private String maxTime = "";

    public PlayingDisplay() {
        borderPane = new BorderPane();

        view = new ImageView();
        view.setFitWidth(512);
        view.setFitHeight(512);

        seekBar = new JFXSlider();
        seekBar.setMaxWidth(400);
        seekBar.setPrefWidth(400);
        seekBar.setMax(0);
        seekBar.setOnMousePressed(e -> {
            SongPlayerController.pause();
        });
        seekBar.setOnMouseReleased(e -> {
            SongPlayerController.seek(seekBar.getValue());
            SongPlayerController.play();
        });

        time = new Text();
        time.setFill(Color.GRAY);
        time.setFont(Font.font("Yu Gothic UI", 12));
        time.setText("0:00/0:00");
        time.setWrappingWidth(100);
        time.setTextAlignment(TextAlignment.RIGHT);

        title = new Text("No played");
        title.setFont(Font.font("Yu Gothic UI", FontWeight.LIGHT, 12));
        title.setFill(Color.WHITE);
        title.setWrappingWidth(300);

        HBox box = new HBox();
        box.setSpacing(16);
        box.setAlignment(Pos.CENTER);
        box.prefWidthProperty().bind(this.widthProperty());
        box.setPrefHeight(32);
        box.setMaxHeight(32);

        box.getChildren().add(title);
        box.getChildren().add(time);

        VBox vbox = new VBox();
        vbox.setPrefHeight(64);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().add(box);
        //vbox.getChildren().add(seekBar);

        back = new Button();
        back.setPrefSize(32, 32);
        back.setGraphic(new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("icons/back.png"))));

        startStop = new Button();
        startStop.setPrefSize(32, 32);
        startStop.setGraphic(new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("icons/pause.png"))));
        startStop.setOnAction(e -> SongPlayerController.toggleStartPause());

        go = new Button();
        go.setPrefSize(32, 32);
        go.setGraphic(new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("icons/go.png"))));

        HBox buttons = new HBox();
        buttons.setPadding(new Insets(16));
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(back, startStop, go);

        volume = new JFXSlider();
        volume.setMaxWidth(100);
        volume.setPrefWidth(100);
        volume.setMax(10);
        volume.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                SongPlayerController.volume(newValue.doubleValue());
            }
        });

        HBox mainBox = new HBox();
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setPrefWidth(400);
        mainBox.setSpacing(16);
        mainBox.setStyle("-fx-background-color: #111111;");

        mainBox.getChildren().add(vbox);

        borderPane.setCenter(view);
        borderPane.setBottom(mainBox);

        this.setId("PlayingDisplay");
        this.getChildren().add(borderPane);
        this.setStyle("-fx-background-color: #212121;");
}

    public void setPlayingData() {
        Map<String, Object> data = SongPlayerController.getSongData();
        title.setText(data.get("title") + " / " + data.get("album") + (data.get("year") != null && !data.get("year").equals("Unknown") ? " (" + data.get("year") + ")" : "") + " - " + data.get("artist"));
    }

    public void setImage(Image image) {
        view.setImage(image);
    }

    public void setMaxTime(double time) {
        maxTime = FormatUtil.secToString(time);
        seekBar.setMax(time);
    }

    public void setTime(double time) {
        seekBar.setValue(time);
    }

    public void setTimeLabel(String timeLabel) {
        time.setText(timeLabel + "/" + maxTime);
    }

}
