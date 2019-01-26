package itsu.java.musicplayerfx.Utils;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import itsu.java.musicplayerfx.GUIManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.text.Text;

public class DialogFactory {

    public static void showDialog(String title, String message) {
        JFXDialogLayout dialogContent = new JFXDialogLayout();
        if (title != null) dialogContent.setHeading(new Text(title));
        if (message != null) dialogContent.setBody(new Text(message));

        JFXButton close = new JFXButton("閉じる");
        close.getStylesheets().add(GUIManager.class.getClassLoader().getResource("css/DialogButton.css").toExternalForm());
        dialogContent.setActions(close);

        JFXDialog dialog = new JFXDialog(GUIManager.getRootPane(), dialogContent, JFXDialog.DialogTransition.CENTER);
        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                dialog.close();
            }
        });
        dialog.show();
    }

}
