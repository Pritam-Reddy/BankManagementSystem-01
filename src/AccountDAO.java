// src/AccountDAO.java
import java.sql.SQLException;
import java.util.List;

public interface AccountDAO {
    void createAccount(Account account) throws SQLException;
    Account getAccount(String accountNumber) throws SQLException;
    void updateAccount(Account account) throws SQLException;
    void deleteAccount(String accountNumber) throws SQLException;
    List<Account> getAllAccounts() throws SQLException;
}