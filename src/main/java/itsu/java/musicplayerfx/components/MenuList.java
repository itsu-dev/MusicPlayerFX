package itsu.java.musicplayerfx.components;

import com.jfoenix.controls.JFXListView;
import itsu.java.musicplayerfx.GUIManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.ArrayList;
import java.util.List;

public class MenuList extends JFXListView<String> {

    private List<String> listData;

    public MenuList() {
        listData = new ArrayList<>();
        listData.add("再生中");
        listData.add("ライブラリ");
        listData.add("プレイリスト");
        listData.add("イコライザ");

        this.getItems().addAll(listData);
        this.setPrefWidth(300);
        this.setMaxWidth(300);
        this.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
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
    }
}
