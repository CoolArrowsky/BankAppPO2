package put.bankapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;


public class ScenesController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    private String address;

    public void switchToSceneLog(String address, ActionEvent event) {
        try {
            this.address = address;

            root = FXMLLoader.load(getClass().getResource(address));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchToSceneLog(String address, MouseEvent mouseEvent) {
        try {
            this.address = address;

            root = FXMLLoader.load(getClass().getResource(address));
            stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return this.address;
    }


}
