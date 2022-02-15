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

public class DepositController {
    Account account;

    @FXML
    private Label labelInformation;

    @FXML
    private TextField textFieldDeposit;

    @FXML
    private Button buttonDeposit;

    @FXML
    void depositOnClick(MouseEvent event) {
        String amount = textFieldDeposit.getText();
        if (amount.isEmpty()) {
            labelInformation.setText("PUSTE POLE!");
            new LabelCleaner(4, labelInformation).startCountdown();
        } else {
            try {
                double number = Double.parseDouble(amount);

                Connection connection = Connect.getConn();
                State.changeValue(connection, account, number + account.getBalance());
                Connect.deleteConn(connection);

                account.setBalance(account.getBalance() + number);
                labelInformation.setText("UDA\u0141O SI\u0118 WP\u0141ACI\u0106 KWOT\u0118");
                textFieldDeposit.clear();
            } catch (NumberFormatException e) {
                labelInformation.setText("\u0179LE SFORMU\u0141OWANA LICZBA");
                new LabelCleaner(4, labelInformation).startCountdown();
                textFieldDeposit.clear();
            }
        }
    }

    void setAccount(Account account) {
        this.account = account;
    }

}

