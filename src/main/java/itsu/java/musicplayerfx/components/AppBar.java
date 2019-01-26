package itsu.java.musicplayerfx.components;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import itsu.java.musicplayerfx.GUIManager;
import javafx.geometry.Insets;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import static javafx.scene.input.MouseEvent.MOUSE_PRESSED;

public class AppBar extends StackPane {

    private FlowPane main;
    private HBox hbox;
    private JFXHamburger h1;
    private Text title;
    private HamburgerSlideCloseTransition burgerTask;

    public AppBar() {
        main = new FlowPane();
        hbox = new HBox();
        h1 = new JFXHamburger();
        title = new Text();
        burgerTask = new HamburgerSlideCloseTransition(h1);

        burgerTask.setRate(-1);
        h1.addEventHandler(MOUSE_PRESSED, e -> {
            GUIManager.getDrawer().toggle();
            burgerTask.setRate(burgerTask.getRate() * -1);
            burgerTask.play();
        });

        title.setText("MusicPlayerFX");
        title.setFill(Color.web("#ffffff"));
        title.setFont(Font.font("Arial", 20));

        //hbox.getChildren().add(h1);
        hbox.getChildren().add(title);

        main.setVgap(20);
        main.setHgap(20);
        main.getChildren().add(hbox);

        this.getChildren().add(main);
        this.setStyle("-fx-background-color:#3F51B5");
        StackPane.setMargin(main, new Insets(15));
    }

}
