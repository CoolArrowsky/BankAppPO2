package put.bankapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import put.bankapp.models.Account;
import put.bankapp.models.SavingAccount;
import put.bankapp.models.TransactionAccount;
import put.bankapp.utils.Connect;
import put.bankapp.utils.State;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable {

    @FXML
    private Button buttonLogin;

    @FXML
    private Button buttonRegister;

    @FXML
    private TextField textFieldCard;

    @FXML
    private PasswordField textFieldPIN;

    @FXML
    private Label informationLabel;

    @FXML
    private AnchorPane scenePane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (Exception e) {
            e.printStackTrace();
        }
        ;
        State.createDBS();
    }

    @FXML
    void actionLog(ActionEvent event) {
        String cardNumber = textFieldCard.getText();
        String pin = textFieldPIN.getText();
        Account account;

        if ((!cardNumber.isEmpty()) && (!pin.isEmpty())) {
            Connection connection = Connect.getConn();
            if (State.checkIfValueExists(connection, cardNumber)) {
                account = State.getAccountByNum(cardNumber);
                try {
                    if (account.getCardID().getPin().equals(pin)) {

                        FXMLLoader fxmlLoader;
                        Parent root;

                        int type = account.getAccountType();
                        if (type == 1) { //tranzakcyjne
                            fxmlLoader = new FXMLLoader(getClass().getResource("/transactAccount.fxml"));
                            root = fxmlLoader.load();

                            TransactionAccount transactionAccount = (TransactionAccount) account;
                            TransactController transactController = fxmlLoader.getController();
                            transactController.setAccount(transactionAccount);
                        } else { //oszczednosciowe
                            fxmlLoader = new FXMLLoader(getClass().getResource("/savingAccount.fxml"));
                            root = fxmlLoader.load();

                            SavingAccount savingAccount = (SavingAccount) account;
                            SavingController savingController = fxmlLoader.getController();
                            savingController.setAccount(savingAccount);
                        }
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

            } else {
                informationLabel.setText("NIEPRAWID\u0141OWE DANE!");
            }
        } else {
            informationLabel.setText("WYPE\u0141NIJ POLA!");
        }
    }

    @FXML
    void exitOnClick(MouseEvent event) {
        Stage stage = (Stage) scenePane.getScene().getWindow();
        stage.close();
    }

    @FXML
    void actionRegister(ActionEvent event) {
        ScenesController scenesController = new ScenesController();
        scenesController.switchToSceneLog("/register.fxml", event);
    }
}
