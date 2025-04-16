// src/AccountDAOImpl.java
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAOImpl implements AccountDAO {

    private Connection connection;

    public AccountDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createAccount(Account account) throws SQLException {
        String sql = "INSERT INTO accounts (account_number, customer_name, balance, account_type) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, account.getAccountNumber());
            statement.setString(2, account.getCustomerName());
            statement.setDouble(3, account.getBalance());
            statement.setString(4, account.getAccountType());
            statement.executeUpdate();
        }
    }

    @Override
    public Account getAccount(String accountNumber) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE account_number = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, accountNumber);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Account(
                        resultSet.getString("account_number"),
                        resultSet.getString("customer_name"),
                        resultSet.getDouble("balance"),
                        resultSet.getString("account_type")
                );
            }
        }
        return null;
    }

    @Override
    public void updateAccount(Account account) throws SQLException {
        String sql = "UPDATE accounts SET customer_name = ?, balance = ?, account_type = ? WHERE account_number = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, account.getCustomerName());
            statement.setDouble(2, account.getBalance());
            statement.setString(3, account.getAccountType());
            statement.setString(4, account.getAccountNumber());
            statement.executeUpdate();
        }
    }

    @Override
    public void deleteAccount(String accountNumber) throws SQLException {
        String sql = "DELETE FROM accounts WHERE account_number = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, accountNumber);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Account with number " + accountNumber + " deleted successfully.");
            } else {
                System.out.println("Account with number " + accountNumber + " not found.");
            }
        }
    }

    @Override
    public List<Account> getAllAccounts() throws SQLException {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                accounts.add(new Account(
                        resultSet.getString("account_number"),
                        resultSet.getString("customer_name"),
                        resultSet.getDouble("balance"),
                        resultSet.getString("account_type")
                ));
            }
        }
        return accounts;
    }
}