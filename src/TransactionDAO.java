// src/TransactionDAO.java
import java.sql.SQLException;
import java.util.List;

public interface TransactionDAO {
    void recordTransaction(Transaction transaction) throws SQLException;
    List<Transaction> getTransactionsForAccount(String accountNumber) throws SQLException;
}