import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAOImpl implements TransactionDAO {

    private Connection connection;

    public TransactionDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void recordTransaction(Transaction transaction) throws SQLException {
        String sql = "INSERT INTO transactions (account_number, transaction_type, amount, transaction_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, transaction.getAccountNumber());
            statement.setString(2, transaction.getTransactionType());
            statement.setDouble(3, transaction.getAmount());
            statement.setTimestamp(4, Timestamp.valueOf(transaction.getTransactionDate()));
            statement.executeUpdate();
        }
    }

    @Override
    public List<Transaction> getTransactionsForAccount(String accountNumber) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT transaction_id, account_number, transaction_type, amount, transaction_date FROM transactions WHERE account_number = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, accountNumber);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                transactions.add(new Transaction(
                        resultSet.getInt("transaction_id"),
                        resultSet.getString("account_number"),
                        resultSet.getString("transaction_type"),
                        resultSet.getDouble("amount"),
                        resultSet.getTimestamp("transaction_date").toLocalDateTime()
                ));
            }
        }
        return transactions;
    }
}