package put.bankapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import put.bankapp.models.Account;
import put.bankapp.utils.Connect;
import put.bankapp.utils.Helper;
import put.bankapp.utils.LabelCleaner;
import put.bankapp.utils.State;

import java.sql.Connection;

public class LoanController {
    Account account;

    private double PERCENTAGE;

    @FXML
    private Button buttonLoan;

    @FXML
    private Label labelInformation;

    @FXML
    private Label labelLoan;

    @FXML
    private TextField textFieldLoan;

    @FXML
    void loanOnClick(MouseEvent event) {
        String amount = textFieldLoan.getText();
        if (amount.isEmpty()) {
            labelInformation.setText("PUSTE POLE!");
            new LabelCleaner(4, labelInformation).startCountdown();
        } else {
            try {
                double value = Double.parseDouble(amount);
                value = Helper.roundNumber(value, 2);
                if (isCorrectValue(value)) {
                    Connection connection = Connect.getConn();
                    State.changeValue(connection, account, account.getBalance() + value);
                    Connect.deleteConn(connection);

                    account.setBalance(account.getBalance() + value);
                    labelInformation.setText("UDA\u0141O SI\u0118 WZI\u0104\u0106 PO\u017BYCZK\u0118");
                    printProposalValue(account);
                    textFieldLoan.clear();
                } else {
                    labelInformation.setText("ZBYT DU\u017BA WARTO\u015A\u0106");
                    new LabelCleaner(4, labelInformation).startCountdown();
                    textFieldLoan.clear();
                }
            } catch (NumberFormatException e) {
                labelInformation.setText("\u0179LE SFORMU\u0141OWANA LICZBA");
                new LabelCleaner(4, labelInformation).startCountdown();
                textFieldLoan.clear();
            }
        }
    }

    private boolean isCorrectValue(double value) {
        if (value < account.getBalance() * PERCENTAGE) {
            return true;
        } else {
            return false;
        }
    }

    public void printProposalValue(Account account) {
        double maxValue = account.getBalance() * PERCENTAGE;
        maxValue = Helper.roundNumber(maxValue, 2);
        String value = String.valueOf(maxValue);

        labelLoan.setText("MAX. PO\u017BYCZKA: " + value);
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setPERCENTAGE(double PERCENTAGE) {
        this.PERCENTAGE = PERCENTAGE;
    }
}
