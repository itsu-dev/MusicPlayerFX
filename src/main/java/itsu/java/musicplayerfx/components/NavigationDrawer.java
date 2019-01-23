package itsu.java.musicplayerfx.components;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawersStack;
import com.jfoenix.controls.JFXListView;
import itsu.java.musicplayerfx.GUIManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class NavigationDrawer extends JFXDrawer {

    private VBox box;
    private StackPane pane;
    private JFXDrawersStack drawersStack;
    private DrawerContent drawerContent;
    private JFXListView<String> list;

    private List<String> listData;

    public NavigationDrawer() {
        listData = new ArrayList<>();
        listData.add("再生中");
        listData.add("ライブラリ");
        listData.add("プレイリスト");
        listData.add("イコライザ");

        box = new VBox();
        drawersStack = new JFXDrawersStack();
        pane = new StackPane();
        list = new JFXListView<>();

        FlowPane content = new FlowPane();

        pane.getStyleClass().add("red-400");
        pane.getChildren().add(drawerContent = new DrawerContent());

        drawersStack.setContent(content);
        content.setMaxSize(300, 500);

        list.getItems().addAll(listData);
        list.setPrefWidth(300);
        list.setMaxWidth(300);
        list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                switch (newValue) {
                    case "再生中": {
                        GUIManager.setDisplay(new PlayingDisplay());
                        break;
                    }
                }
            }
        });

        box.getChildren().add(pane);
        box.getChildren().add(list);
        box.setStyle("-fx-background-color: #161616;");

        this.setSidePane(box);
        this.setDefaultDrawerSize(300);
        this.setResizeContent(true);
        this.setResizableOnDrag(true);
        this.setOverLayVisible(false);
        this.setMaxWidth(300);
        this.setId("LEFT");
    }

    public JFXDrawersStack getDrawersStack() {
        return drawersStack;
    }

    public void toggle() {
        drawersStack.toggle(this);
    }

    public DrawerContent getDrawerContent() {
        return drawerContent;
    }
}
