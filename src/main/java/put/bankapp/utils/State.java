package put.bankapp.utils;

import put.bankapp.models.Account;
import put.bankapp.models.CardID;
import put.bankapp.models.SavingAccount;
import put.bankapp.models.TransactionAccount;

import java.sql.*;

public class State {

    private static final String tableSql = "CREATE TABLE IF NOT EXISTS card " +
            "(ID INTEGER PRIMARY KEY AUTOINCREMENT, number TEXT, pin TEXT, balance FLOAT DEFAULT 0, " +
            "name TEXT, age TEXT, accountType INTEGER)";

    private static final String tableCheckIfValue = "SELECT 1 FROM card WHERE number = ?";

    private static final String tableInsert = "INSERT INTO card (number, pin, balance, name, age, accountType) " +
            "VALUES(?,?,?,?,?,?)";

    private static final String tableGetAccount = "SELECT pin, balance, name, age, accountType " +
            "FROM card WHERE number = ?";

    private static final String tableDelete = "DELETE FROM card WHERE number = ?";

    private static final String tableChange = "UPDATE card SET balance = ? WHERE number = ?";

    public static Statement getStatement(Connection connection) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }

    public static void deleteStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void createIfNotExist(Statement statement) {
        if (statement != null) {
            try {
                statement.executeUpdate(tableSql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void createDBS() {
        Connection connection = Connect.getConn();
        Statement statement;
        if (connection != null) {
            statement = getStatement(connection);
            createIfNotExist(statement);
            deleteStatement(statement);
        }
        Connect.deleteConn(connection);
    }

    public static boolean checkIfValueExists(Connection connection, String cardNumber) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(tableCheckIfValue)) {
            preparedStatement.setString(1, cardNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return true;
                } else {
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static boolean insertValue(Connection connection, Account account) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(tableInsert)) {
            String cardNumber = account.getCardID().getCardNumber();
            String cardPin = account.getCardID().getPin();
            double cardBalance = account.getBalance();
            String name = account.getAccountOwner();
            String age = account.getAccountOwnerAge();
            int accountType = account.getAccountType();

            preparedStatement.setString(1, cardNumber);
            preparedStatement.setString(2, cardPin);
            preparedStatement.setDouble(3, cardBalance);
            preparedStatement.setString(4, name);
            preparedStatement.setString(5, age);
            preparedStatement.setInt(6, accountType);

            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean removeValue(Connection connection, Account account) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(tableDelete)) {
            String cardNumber = account.getCardID().getCardNumber();
            preparedStatement.setString(1, cardNumber);

            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean changeValue(Connection connection, Account account, double value) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(tableChange)) {
            String cardNumber = account.getCardID().getCardNumber();
            double finalValue = value;
            preparedStatement.setDouble(1, finalValue);
            preparedStatement.setString(2, cardNumber);

            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean tryToAddRecord(Connection connection, Account account) {
        String cardNumber = account.getCardID().getCardNumber();
        if (checkIfValueExists(connection, cardNumber)) {
            return false;
        } else {
            boolean bool = insertValue(connection, account);
            return bool;
        }
    }

    public static boolean tryToRemoveRecord(Connection connection, Account account) {
        String cardNumber = account.getCardID().getCardNumber();
        if (checkIfValueExists(connection, cardNumber)) {
            boolean bool = removeValue(connection, account);
            return bool;
        } else {
            return false;
        }
    }

    public static boolean tryToChangeValue(Connection connection, Account account, double value) {
        String cardNumber = account.getCardID().getCardNumber();
        if (checkIfValueExists(connection, cardNumber)) {
            boolean bool = changeValue(connection, account, value);
            return bool;
        } else {
            return false;
        }
    }

    public static boolean updateDBS(Account account) {
        Connection connection = Connect.getConn();
        if (tryToAddRecord(connection, account)) {
            Connect.deleteConn(connection);
            return true;
        } else {
            Connect.deleteConn(connection);
            return false;
        }
    }

    public static boolean updateDeleteDBS(Account account) {
        Connection connection = Connect.getConn();
        if (tryToRemoveRecord(connection, account)) {
            Connect.deleteConn(connection);
            return true;
        } else {
            Connect.deleteConn(connection);
            return false;
        }
    }

    public static boolean updateChangeValDBS(Account account, double value) {
        Connection connection = Connect.getConn();
        if (tryToChangeValue(connection, account, value)) {
            Connect.deleteConn(connection);
            return true;
        } else {
            Connect.deleteConn(connection);
            return false;
        }
    }


    public static Account getAccountByNum(String cardNumber) {
        Connection connection = Connect.getConn();
        try (PreparedStatement preparedStatement = connection.prepareStatement(tableGetAccount)) {
            preparedStatement.setString(1, cardNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String cardPin = resultSet.getString("pin");
                double cardBalance = resultSet.getDouble("balance");
                String accountOwner = resultSet.getString("name");
                String age = resultSet.getString("age");
                int accountType = resultSet.getInt("accountType"); //1- transact 2- saving

                if (accountType == 1) {
                    return new TransactionAccount(accountOwner, age, cardBalance, new CardID(cardNumber, cardPin));
                } else if (accountType == 2) {
                    return new SavingAccount(accountOwner, age, cardBalance, new CardID(cardNumber, cardPin));
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            Connect.deleteConn(connection);
        }
    }


}
