package itsu.java.musicplayerfx.components;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawersStack;
import itsu.java.musicplayerfx.GUIManager;
import itsu.java.musicplayerfx.listener.action.TweetButtonActionListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.util.Timer;
import java.util.TimerTask;

public class NavigationDrawer extends JFXDrawer {

    private AnchorPane content;
    private VBox box;
    private StackPane pane;
    private JFXDrawersStack drawersStack;
    private DrawerContent drawerContent;
    private JFXButton tweet;
    private MenuList list;

    public NavigationDrawer() {

        box = new VBox();
        drawersStack = new JFXDrawersStack();
        pane = new StackPane();
        tweet = new JFXButton();
        list = new MenuList();

        content = new AnchorPane();
        /*
        AnchorPane.setTopAnchor(content, 0d);
        AnchorPane.setBottomAnchor(content, 0d);
        AnchorPane.setLeftAnchor(content, 0d);
        AnchorPane.setRightAnchor(content, 0d);*/
        pane.getStyleClass().add("red-400");
        pane.getChildren().add(drawerContent = new DrawerContent());

        drawersStack.setContent(content);
        drawersStack.setStyle("-fx-background-color: transparent;");
        content.setMaxSize(300, 500);

        tweet.getStylesheets().add(this.getClass().getClassLoader().getResource("css/TweetButton.css").toExternalForm());
        tweet.setMaxWidth(300);
        tweet.setPrefWidth(300);
        tweet.setMaxHeight(45);
        tweet.setPrefHeight(45);
        tweet.setText("ツイート");
        tweet.setAlignment(Pos.CENTER_LEFT);
        tweet.setPadding(new Insets(0, 0, 0, 13));
        tweet.setOnAction(new TweetButtonActionListener());

        box.getChildren().add(pane);
        box.getChildren().add(tweet);
        box.getChildren().add(list);
        box.setStyle("-fx-background-color: #161616;");

        this.setSidePane(box);
        this.setMiniDrawerSize(300);
        this.setDefaultDrawerSize(300);
        this.setResizeContent(true);
        this.setResizableOnDrag(true);
        this.setOverLayVisible(false);
        this.setMaxWidth(300);
        this.setId("LEFT");
        this.setStyle("-fx-background-color: #161616;");
        this.toggle();
    }

    public JFXDrawersStack getDrawersStack() {
        return drawersStack;
    }

    public AnchorPane getContentArea() {
        return content;
    }

    public void toggle() {
        drawersStack.toggle(this);
    }

    public DrawerContent getDrawerContent() {
        return drawerContent;
    }
}
