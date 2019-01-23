package itsu.java.musicplayerfx.components;

import com.jfoenix.controls.JFXSlider;
import itsu.java.musicplayerfx.SongPlayerController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class DrawerContent extends VBox {

    private static final double LINE_WIDTH = 268 / 128.0;

    private ImageView albumImage;
    private Text songName;
    private Text albumName;
    private JFXSlider seekBar;
    private Text nowTime;
    private Text time;
    private JFXSlider volume;
    private Canvas canvas;

    private Button back;
    private Button startStop;
    private Button go;

    public DrawerContent() {
        super(16);

        albumImage = new ImageView();
        albumImage.setFitWidth(268);
        albumImage.setFitHeight(268);
        albumImage.setImage(SongPlayerController.getAlbumArkWork());

        songName = new Text();
        songName.setWrappingWidth(268);
        songName.setFill(Color.web("#ffffff"));
        songName.setFont(Font.font("Meiryo UI", 18));
        songName.setTextAlignment(TextAlignment.CENTER);

        albumName = new Text();
        albumName.setWrappingWidth(268);
        albumName.setFill(Color.web("#ffffff"));
        albumName.setFont(Font.font("Meiryo UI", 12));
        albumName.setTextAlignment(TextAlignment.CENTER);

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

        HBox manage = new HBox();
        manage.getChildren().add(back);
        manage.getChildren().add(startStop);
        manage.getChildren().add(go);
        manage.getChildren().add(volume);
        manage.setSpacing(8);
        manage.setAlignment(Pos.CENTER);
        manage.setStyle("-fx-background-color: #161616;");

        seekBar = new JFXSlider();
        seekBar.setMaxWidth(260);
        seekBar.setPrefWidth(260);
        seekBar.setMax(0);
        seekBar.setOnMousePressed(e -> {
            SongPlayerController.pause();
        });
        seekBar.setOnMouseReleased(e -> {
            SongPlayerController.seek(seekBar.getValue());
            SongPlayerController.play();
        });

        nowTime = new Text();
        nowTime.setFill(Color.WHITE);
        nowTime.setFont(Font.font("Meiryo UI", 12));
        nowTime.setText("0:00");

        time = new Text();
        time.setFill(Color.WHITE);
        time.setFont(Font.font("Meiryo UI", 12));
        time.setText("0:00");

        HBox box = new HBox(16);
        box.getChildren().add(nowTime);
        box.getChildren().add(seekBar);
        box.getChildren().add(time);
        box.setStyle("-fx-background-color: #161616;");

        canvas = new Canvas(128 * LINE_WIDTH, 60);
        canvas.setVisible(false);

        this.getChildren().add(albumImage);
        this.getChildren().add(songName);
        this.getChildren().add(albumName);
        this.getChildren().add(manage);
        this.getChildren().add(box);
        this.getChildren().add(canvas);
        this.setPadding(new Insets(16));
        this.setStyle("-fx-background-color: #161616;");
    }

    public void setAlbumImage(Image image) {
        albumImage.setImage(image);
    }

    public void setSongName(String name) {
        songName.setText(name);
    }

    public void setAlbumName(String name) {
        albumName.setText(name);
    }

    public void setMaxTime(double time) {
        seekBar.setMax(time);
    }

    public void setTime(double time) {
        seekBar.setValue(time);
    }

    public void setMaxTimeLabel(String timeLabel) {
        time.setText(timeLabel);
    }

    public void setTimeLabel(String timeLabel) {
        nowTime.setText(timeLabel);
    }

    //set playing true
    //set pausing false
    public void toggleStartStop(boolean bool) {
        if (bool) {
            startStop.setGraphic(new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("icons/pause.png"))));
        } else {
            startStop.setGraphic(new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("icons/play.png"))));
        }
    }

    public void createSpectrum() {
        canvas.setVisible(true);

        AudioSpectrumListener listener = new AudioSpectrumListener() {
            private AudioSpectrumListener beforeListener;

            {
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
        };

        SongPlayerController.setAudipSpectrumListener(listener);
    }

}
