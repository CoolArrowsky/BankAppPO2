package put.bankapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import put.bankapp.models.CardID;
import put.bankapp.models.SavingAccount;
import put.bankapp.models.TransactionAccount;
import put.bankapp.utils.Connect;
import put.bankapp.utils.State;

import java.sql.Connection;

public class RegisterController {
    @FXML
    Button buttonNext;

    @FXML
    RadioButton transactButton;

    @FXML
    RadioButton savingButton;

    @FXML
    TextField nameTextField;

    @FXML
    ToggleGroup accountGroup;

    @FXML
    TextField ageTextField;

    @FXML
    Label informationLabel;

    @FXML
    AnchorPane scenePane;

    @FXML
    public void nextButtonClicked(MouseEvent mouseEvent) {
        RadioButton pickedButton = (RadioButton) accountGroup.getSelectedToggle();
        String name = nameTextField.getText();
        String age = ageTextField.getText();

        try {
            if (pickedButton != null && !name.isEmpty() && !age.isEmpty()) {
                if (checkIfValidAge(age) && checkIfValidName(name)) {
                    String NEXT_SCENE_ADDRESS = "/successfulRegister.fxml";
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(NEXT_SCENE_ADDRESS));
                    Parent root = fxmlLoader.load();
                    SuccessfulRegisterController successfulRegisterController = fxmlLoader.getController();
                    Connection connection;
                    if (pickedButton.getText().equals("Tranzakcyjne")) {
                        TransactionAccount transactionAccount = new TransactionAccount(name, age, 0, new CardID());
                        //Dodajemy uzyskane konto do dbs
                        if ((connection = Connect.getConn()) != null) {
                            if (State.insertValue(connection, transactionAccount))
                                successfulRegisterController.displayAllElements(transactionAccount);
                        }
                        Connect.deleteConn(connection);
                    } else { //Oszczednosciowe
                        SavingAccount savingAccount = new SavingAccount(name, age, 0, new CardID());
                        //Dodajemy uzyskane konto do dbs
                        if ((connection = Connect.getConn()) != null) {
                            if (State.insertValue(connection, savingAccount))
                                successfulRegisterController.displayAllElements(savingAccount);
                        }
                        Connect.deleteConn(connection);
                    }
                    Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } else if (!checkIfValidAge(age)) {
                    informationLabel.setText("NIEPRAWID\u0141OWY WIEK!");
                } else {
                    informationLabel.setText("ZA D\u0141UGA NAZWA!");
                }
            } else {
                informationLabel.setText("WYPE\u0141NIJ POLA!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void exitOnClick(MouseEvent event) {
        Stage stage = (Stage) scenePane.getScene().getWindow();
        stage.close();
    }

    public boolean checkIfValidAge(String age) {
        try {
            int ageNumber = Integer.parseInt(age);
            if ((ageNumber < 100) && (ageNumber >= 18)) {
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    public boolean checkIfValidName(String name) {
        if (name.length() >= 20) {
            return false;
        }
        return true;
    }
}
