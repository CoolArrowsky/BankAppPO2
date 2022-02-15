package put.bankapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import put.bankapp.models.Account;


public class SuccessfulRegisterController {

    @FXML
    private Label accountNumberPIN;

    @FXML
    private Label accountOwnerAge;

    @FXML
    private Label accountOwnerName;

    @FXML
    private Label accountType;

    @FXML
    private Button nextButton;

    public void displayAllElements(Account account) {
        Integer accountType = account.getAccountType();
        this.accountNumberPIN.setText(account.getCardID().getCardNumber() + " || " + account.getCardID().getPin());
        this.accountOwnerAge.setText(account.getAccountOwnerAge());
        this.accountOwnerName.setText(account.getAccountOwner());
        if (accountType == 1) {
            this.accountType.setText("Tranzakcyjne");
        } else {
            this.accountType.setText("Oszcz\u0119dno\u015Bciowe");
        }
    }

    @FXML
    void nextButtonClicked(MouseEvent mouseEvent) {
        ScenesController scenesController = new ScenesController();
        scenesController.switchToSceneLog("/welcome.fxml", mouseEvent);
    }

}
