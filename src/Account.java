public class Account {
  private String accountNumber;
  private String customerName;
  private double balance;
  private String accountType;

  public Account(String accountNumber, String customerName, double balance, String accountType) {
      this.accountNumber = accountNumber;
      this.customerName = customerName;
      this.balance = balance;
      this.accountType = accountType;
  }

  // Getters and Setters
  public String getAccountNumber() {
      return accountNumber;
  }

  public String getCustomerName() {
      return customerName;
  }

  public double getBalance() {
      return balance;
  }

/*************  ✨ Windsurf Command ⭐  *************/
  /**
   * Gets the type of account.
   * @return The account type. The possible values are "checking" and "savings".
   */
/*******  8de21db0-2463-4c77-bb89-643dd408fc0e  *******/
  public String getAccountType() {
      return accountType;
  }

  public void deposit(double amount) {
      if (amount > 0) {
          balance += amount;
          System.out.println("Deposit of $" + amount + " successful. New balance: $" + balance);
      } else {
          System.out.println("Invalid deposit amount.");
      }
  }

  public boolean withdraw(double amount) {
      if (amount > 0 && balance >= amount) {
          balance -= amount;
          System.out.println("Withdrawal of $" + amount + " successful. New balance: $" + balance);
          return true;
      } else if (amount <= 0) {
          System.out.println("Invalid withdrawal amount.");
      } else {
          System.out.println("Insufficient balance.");
      }
      return false;
  }

  public void displayAccountInfo() {
      System.out.println("Account Number: " + accountNumber);
      System.out.println("Customer Name: " + customerName);
      System.out.println("Account Type: " + accountType);
      System.out.println("Balance: $" + balance);
  }
}