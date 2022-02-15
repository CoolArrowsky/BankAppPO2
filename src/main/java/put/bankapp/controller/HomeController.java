package put.bankapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import put.bankapp.models.Account;

import java.text.DecimalFormat;


public class HomeController {
    private static final DecimalFormat df = new DecimalFormat("0.00");

    @FXML
    private Label labelAge;

    @FXML
    private Label labelCardNumber;

    @FXML
    private Label labelName;

    @FXML
    private Label labelPin;

    @FXML
    private Label labelBalance;

    public void displayAllElements(Account account) {
        if (account != null) {
            //Integer accountType = account.getAccountType();
            this.labelPin.setText(account.getCardID().getPin());
            this.labelCardNumber.setText(account.getCardID().getCardNumber());
            this.labelAge.setText(account.getAccountOwnerAge());
            this.labelName.setText(account.getAccountOwner());
            String formattedBalance = df.format(account.getBalance());

            this.labelBalance.setText(formattedBalance);
        }
    }
}
