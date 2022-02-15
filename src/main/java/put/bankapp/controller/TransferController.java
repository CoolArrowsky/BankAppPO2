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

public class TransferController {
    Account account;

    @FXML
    private Button buttonTransfer;

    @FXML
    private Label labelInformation;

    @FXML
    private TextField textFieldCardNumber;

    @FXML
    private TextField textFieldPay;

    @FXML
    void transferOnClick(MouseEvent event) {
        String cardNumber = textFieldCardNumber.getText();
        String amount = textFieldPay.getText();
        if (cardNumber.isEmpty() || amount.isEmpty()) {
            labelInformation.setText("PUSTE POLA!");
            new LabelCleaner(4, labelInformation).startCountdown();
        } else if (cardNumber.equals(account.getCardID().getCardNumber())) {
            labelInformation.setText("TO TWOJE KONTO");
            new LabelCleaner(4, labelInformation).startCountdown();
        } else {
            Connection connection = Connect.getConn();
            boolean isRecordInDB = State.checkIfValueExists(connection, cardNumber);
            Connect.deleteConn(connection);
            if (isRecordInDB) {
                Account receiverAccount = State.getAccountByNum(cardNumber);
                try {
                    double number = Double.parseDouble(amount);
                    if (number > account.getBalance()) {
                        labelInformation.setText("NIE POSIADASZ TAKIEJ KWOTY");
                        new LabelCleaner(4, labelInformation).startCountdown();
                    } else {
                        Connection connectionSecond = Connect.getConn();
                        State.changeValue(connectionSecond, account, account.getBalance() - number);
                        State.changeValue(connectionSecond, receiverAccount, receiverAccount.getBalance() + number);

                        account.setBalance(account.getBalance() - number);
                        labelInformation.setText("UDA\u0141O SI\u0118 WP\u0141ACI\u0106 KWOT\u0118");
                        textFieldCardNumber.clear();
                        textFieldPay.clear();
                    }
                } catch (NumberFormatException exception) {
                    labelInformation.setText("\u0179LE SFORMU\u0141OWANA LICZBA");
                    new LabelCleaner(4, labelInformation).startCountdown();
                    textFieldPay.clear();
                    textFieldCardNumber.clear();
                }
            } else {
                labelInformation.setText("NIEISTNIEJ\u0104CY ODBIORCA");
                new LabelCleaner(4, labelInformation).startCountdown();
                textFieldPay.clear();
                textFieldCardNumber.clear();
            }
        }
    }

    void setAccount(Account account) {
        this.account = account;
    }

}
