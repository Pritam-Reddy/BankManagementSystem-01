import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Bank {
    private AccountDAO accountDAO;
    private TransactionDAO transactionDAO;

    public Bank(Connection connection) {
        this.accountDAO = new AccountDAOImpl(connection);
        this.transactionDAO = new TransactionDAOImpl(connection);
    }

    public void deleteAccount(String accountNumber) {
        try {
            accountDAO.deleteAccount(accountNumber);
        } catch (SQLException e) {
            System.err.println("Error deleting account: " + e.getMessage());
        }
    }

    public void createAccount(String accountNumber, String customerName, double initialBalance, String accountType) {
        Account newAccount = new Account(accountNumber, customerName, initialBalance, accountType);
        try {
            accountDAO.createAccount(newAccount);
            System.out.println("Account created successfully with account number: " + accountNumber);
        } catch (SQLException e) {
            System.err.println("Error creating account: " + e.getMessage());
        }
    }

    public Account getAccount(String accountNumber) {
        try {
            return accountDAO.getAccount(accountNumber);
        } catch (SQLException e) {
            System.err.println("Error retrieving account: " + e.getMessage());
            return null;
        }
    }

    public void deposit(String accountNumber, double amount) {
        try {
            Account account = accountDAO.getAccount(accountNumber);
            if (account != null) {
                account.deposit(amount);
                accountDAO.updateAccount(account);

                // Record the transaction
                Transaction transaction = new Transaction(accountNumber, "deposit", amount);
                transactionDAO.recordTransaction(transaction);
                System.out.println("Deposit of $" + amount + " recorded.");

            } else {
                System.out.println("Account with number " + accountNumber + " not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error during deposit: " + e.getMessage());
        }
    }

    public void withdraw(String accountNumber, double amount) {
        try {
            Account account = accountDAO.getAccount(accountNumber);
            if (account != null) {
                if (account.withdraw(amount)) {
                    accountDAO.updateAccount(account);

                    // Record the transaction
                    Transaction transaction = new Transaction(accountNumber, "withdrawal", amount);
                    transactionDAO.recordTransaction(transaction);
                    System.out.println("Withdrawal of $" + amount + " recorded.");
                }
            } else {
                System.out.println("Account with number " + accountNumber + " not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error during withdrawal: " + e.getMessage());
        }
    }

    public void checkBalance(String accountNumber) {
        Account account = getAccount(accountNumber);
        if (account != null) {
            System.out.print("Account Number: " + accountNumber + ", ");
            account.displayAccountInfo();
        } else {
            System.out.println("Account with number " + accountNumber + " not found.");
        }
    }

    public void displayAllAccounts() {
        try {
            List<Account> allAccounts = accountDAO.getAllAccounts();
            if (allAccounts.isEmpty()) {
                System.out.println("No accounts found.");
            } else {
                System.out.println("--- All Accounts ---");
                for (Account account : allAccounts) {
                    account.displayAccountInfo();
                    System.out.println("--------------------");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving all accounts: " + e.getMessage());
        }
    }

    public void displayTransactions(String accountNumber) {
        try {
            List<Transaction> transactions = transactionDAO.getTransactionsForAccount(accountNumber);
            if (transactions.isEmpty()) {
                System.out.println("No transactions found for account: " + accountNumber);
            } else {
                System.out.println("--- Transactions for Account: " + accountNumber + " ---");
                for (Transaction transaction : transactions) {
                    System.out.println("Transaction ID: " + transaction.getTransactionId());
                    System.out.println("Type: " + transaction.getTransactionType());
                    System.out.println("Amount: $" + transaction.getAmount());
                    System.out.println("Date: " + transaction.getTransactionDate());
                    System.out.println("-----------------------------------------");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving transactions: " + e.getMessage());
        }
    }
}