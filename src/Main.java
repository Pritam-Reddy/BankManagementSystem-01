// src/Main.java
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connection connection = null;
        Bank bank = null;

        try {
            connection = DatabaseConnection.getConnection();
            bank = new Bank(connection);

            while (true) {
                System.out.println("\nBank Management System Menu:");
                System.out.println("1. Create Account");
                System.out.println("2. Deposit");
                System.out.println("3. Withdraw");
                System.out.println("4. Check Balance");
                System.out.println("5. Display All Accounts");
                System.out.println("6. Display Transactions for Account");
                System.out.println("7. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        System.out.print("Enter Account Number: ");
                        String accountNumber = scanner.nextLine();
                        System.out.print("Enter Customer Name: ");
                        String customerName = scanner.nextLine();
                        System.out.print("Enter Initial Balance: ");
                        double initialBalance = scanner.nextDouble();
                        scanner.nextLine(); // Consume newline
                        System.out.print("Enter Account Type (Savings/Current): ");
                        String accountType = scanner.nextLine();
                        bank.createAccount(accountNumber, customerName, initialBalance, accountType);
                        break;
                    case 2:
                        System.out.print("Enter Account Number: ");
                        accountNumber = scanner.nextLine();
                        System.out.print("Enter Deposit Amount: ");
                        double depositAmount = scanner.nextDouble();
                        scanner.nextLine(); // Consume newline
                        bank.deposit(accountNumber, depositAmount);
                        break;
                    case 3:
                        System.out.print("Enter Account Number: ");
                        accountNumber = scanner.nextLine();
                        System.out.print("Enter Withdrawal Amount: ");
                        double withdrawAmount = scanner.nextDouble();
                        scanner.nextLine(); // Consume newline
                        bank.withdraw(accountNumber, withdrawAmount);
                        break;
                    case 4:
                        System.out.print("Enter Account Number: ");
                        accountNumber = scanner.nextLine();
                        bank.checkBalance(accountNumber);
                        break;
                    case 5:
                        bank.displayAllAccounts();
                        break;
                    case 6:
                        System.out.print("Enter Account Number: ");
                        accountNumber = scanner.nextLine();
                        bank.displayTransactions(accountNumber);
                        break;
                    case 7:
                        System.out.println("Exiting Bank Management System. Goodbye!");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }

        } catch (SQLException e) {
            System.err.println("Error during bank operations: " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    DatabaseConnection.closeConnection();
                } catch (SQLException e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
        }
    }
}