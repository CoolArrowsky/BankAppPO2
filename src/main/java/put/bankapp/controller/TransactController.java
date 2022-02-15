package put.bankapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import put.bankapp.models.Account;
import put.bankapp.models.TransactionAccount;

import java.io.IOException;
import java.util.List;

public class TransactController {
    private Account account;
    private FXMLLoader paneLoader;

    @FXML
    private Text buttonLoan;

    @FXML
    private Text buttonTransfer;

    @FXML
    private Text buttonDeposit;

    @FXML
    private Text buttonMain;

    @FXML
    private Text buttonWithdraw;

    @FXML
    private AnchorPane scenePane;

    @FXML
    private Pane changePane;

    @FXML
    void homeOnClick(MouseEvent event) {
        setFxmlLoader(new FXMLLoader(getClass().getResource("/home.fxml")));
        loadFxml();

        HomeController homeController = paneLoader.getController();
        homeController.displayAllElements(account);

    }

    @FXML
    void depositOnClick(MouseEvent event) {
        setFxmlLoader(new FXMLLoader(getClass().getResource("/deposit.fxml")));
        loadFxml();

        DepositController depositController = paneLoader.getController();
        depositController.setAccount(account);
    }

    @FXML
    void withdrawOnClick(MouseEvent event) {
        setFxmlLoader(new FXMLLoader(getClass().getResource("/withdraw.fxml")));
        loadFxml();

        WithdrawController withdrawController = paneLoader.getController();
        withdrawController.setAccount(account);
    }

    @FXML
    void loanOnClick(MouseEvent event) {
        setFxmlLoader(new FXMLLoader(getClass().getResource("/loan.fxml")));
        loadFxml();

        LoanController loanController = paneLoader.getController();
        loanController.setPERCENTAGE(TransactionAccount.getPERCENTAGE());
        loanController.printProposalValue(account);
        loanController.setAccount(account);
    }

    @FXML
    void transferOnClick(MouseEvent event) {
        setFxmlLoader(new FXMLLoader(getClass().getResource("/transfer.fxml")));
        loadFxml();

        TransferController transferController = paneLoader.getController();
        transferController.setAccount(account);
    }

    @FXML
    void exitOnClick(MouseEvent event) {
        Stage stage = (Stage) scenePane.getScene().getWindow();
        stage.close();
    }

    @FXML
    void logoutOnClick(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/welcome.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) scenePane.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void loadFxml() {
        try {
            Pane newPane = paneLoader.load();
            List<Node> parentChildren = ((Pane) changePane.getParent()).getChildren();
            parentChildren.set(parentChildren.indexOf(changePane), newPane);
            changePane = newPane;
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public Account getAccount() {
        return account;
    }

    public FXMLLoader getFxmlLoader() {
        return paneLoader;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setFxmlLoader(FXMLLoader fxmlLoader) {
        this.paneLoader = fxmlLoader;
    }
}
