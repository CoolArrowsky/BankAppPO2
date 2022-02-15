package put.bankapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import put.bankapp.models.Account;
import put.bankapp.utils.Connect;
import put.bankapp.utils.Helper;
import put.bankapp.utils.LabelCleaner;
import put.bankapp.utils.State;

import java.sql.Connection;

public class CapitalizationController {
    private Account account;
    private double PERCENTAGE;

    @FXML
    private Button buttonCapitalization;

    @FXML
    private Label labelAfterBalance;

    @FXML
    private Label labelCurrentBalance;

    @FXML
    private Label labelStatus;

    @FXML
    void capitalizationOnClick(MouseEvent event) {
        double newValue = account.getBalance() + account.getBalance() * PERCENTAGE;
        newValue = Helper.roundNumber(newValue, 2);

        double nextValue = newValue + newValue * PERCENTAGE;
        nextValue = Helper.roundNumber(nextValue, 2);

        String newValueString = String.valueOf(newValue);
        String nextValueString = String.valueOf(nextValue);

        labelCurrentBalance.setText("MASZ OBECNIE NA KONCIE: " + newValueString);
        labelAfterBalance.setText("PO KOLEJNEJ KAPITALIZACJI: " + nextValueString);

        Connection connection = Connect.getConn();
        State.changeValue(connection, account, newValue);
        Connect.deleteConn(connection);

        account.setBalance(newValue);

        labelStatus.setText("KAPITALIZACJA UDANA!");
        new LabelCleaner(4, labelStatus).startCountdown();
    }

    public void setStartLabels(double balance) {
        double newValue = balance + balance * PERCENTAGE;
        newValue = Helper.roundNumber(newValue, 2);

        String balanceString = String.valueOf(balance);
        String newValueString = String.valueOf(newValue);

        labelCurrentBalance.setText("MASZ OBECNIE NA KONCIE: " + balanceString);
        labelAfterBalance.setText("PO KOLEJNEJ KAPITALIZACJI: " + newValueString);
    }

    public void setPERCENTAGE(double percentage) {
        this.PERCENTAGE = percentage;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

}
