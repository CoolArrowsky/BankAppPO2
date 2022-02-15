package put.bankapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import put.bankapp.models.Account;
import put.bankapp.utils.Connect;
import put.bankapp.utils.LabelCleaner;
import put.bankapp.utils.State;

import java.sql.Connection;

public class WithdrawController {
    Account account;

    @FXML
    private Label labelInformation;

    @FXML
    private TextField textFieldWithdraw;

    @FXML
    private Button buttonWithdraw;

    @FXML
    void withdrawOnClick(MouseEvent event) {
        String amount = textFieldWithdraw.getText();
        if (amount.isEmpty()) {
            labelInformation.setText("PUSTE POLE!");
            new LabelCleaner(4, labelInformation).startCountdown();
        } else {
            try {
                double number = Double.parseDouble(amount);
                if (number > account.getBalance()) {
                    labelInformation.setText("NIE POSIADASZ TAKIEJ KWOTY!");
                    new LabelCleaner(4, labelInformation).startCountdown();
                } else {
                    Connection connection = Connect.getConn();
                    State.changeValue(connection, account, account.getBalance() - number);
                    account.setBalance(account.getBalance() - number);
                    labelInformation.setText("UDA\u0141O SI\u0118 WP\u0141ACI\u0106 KWOT\u0118");
                    textFieldWithdraw.clear();
                }
            } catch (NumberFormatException e) {
                labelInformation.setText("\u0179LE SFORMU\u0141OWANA LICZBA");
                new LabelCleaner(4, labelInformation).startCountdown();
                textFieldWithdraw.clear();
            }
        }
    }

    void setAccount(Account account) {
        this.account = account;
    }

}