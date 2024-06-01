Create a banking system simulation where users can open accounts, make 
transactions, and view their account balances. Implement features like interest 
calculation and account types
Program –
import java.util.ArrayList;
import java.util.List;
abstract class Account {
 private String accountNumber;
 private double balance;
 private List<String> transactions;
 public Account(String accountNumber, double initialBalance) {
 this.accountNumber = accountNumber;
 this.balance = initialBalance;
 this.transactions = new ArrayList<>();
 addTransaction("Account opened with balance: " + initialBalance);
 }
 public String getAccountNumber() {
 return accountNumber;
 }
 public double getBalance() {
 return balance;
 }
 protected void addTransaction(String transaction) {
 transactions.add(transaction);
 }
 public List<String> getTransactions() {
 return transactions;
 }
 public void deposit(double amount) {
 if (amount > 0) {
 balance += amount;
 addTransaction("Deposited: " + amount);
 } else {
 System.out.println("Invalid deposit amount.");
 }
 }
 public void withdraw(double amount) {
 if (amount > 0 && balance >= amount) {
 balance -= amount;
 addTransaction("Withdrew: " + amount);
 } else {
 System.out.println("Invalid withdrawal amount or insufficient balance.");
 }
 }
 public abstract void applyInterest();
}
class SavingsAccount extends Account {
 private double interestRate;
 public SavingsAccount(String accountNumber, double initialBalance, double 
interestRate) {
 super(accountNumber, initialBalance);
 this.interestRate = interestRate;
 }
 @Override
 public void applyInterest() {
 double interest = getBalance() * interestRate / 100;
 deposit(interest);
 addTransaction("Interest applied: " + interest);
 }
}
Define the SavingsAccount and CheckingAccount Classes
java
class CheckingAccount extends Account {
 public CheckingAccount(String accountNumber, double initialBalance) {
 super(accountNumber, initialBalance);
 }
 @Override
 public void applyInterest() {
 // Checking accounts typically do not have interest
 }
}
Define the Bank Class
import java.util.HashMap;
import java.util.Map;
class Bank {
 private Map<String, Account> accounts;
 public Bank() {
 accounts = new HashMap<>();
 }
 public void openSavingsAccount(String accountNumber, double initialBalance, 
double interestRate) {
 if (!accounts.containsKey(accountNumber)) {
 accounts.put(accountNumber, new SavingsAccount(accountNumber, 
initialBalance, interestRate));
 System.out.println("Savings account opened with account number: " + 
accountNumber);
 } else {
 System.out.println("Account number already exists.");
 }
 }
 public void openCheckingAccount(String accountNumber, double initialBalance) 
{
 if (!accounts.containsKey(accountNumber)) {
 accounts.put(accountNumber, new CheckingAccount(accountNumber, 
initialBalance));
 System.out.println("Checking account opened with account number: " + 
accountNumber);
 } else {
 System.out.println("Account number already exists.");
 }
 }
 public void deposit(String accountNumber, double amount) {
 Account account = accounts.get(accountNumber);
 if (account != null) {
 account.deposit(amount);
 System.out.println("Deposited " + amount + " to account number: " + 
accountNumber);
 } else {
 System.out.println("Account not found.");
 }
 }
 public void withdraw(String accountNumber, double amount) {
 Account account = accounts.get(accountNumber);
 if (account != null) {
 account.withdraw(amount);
 System.out.println("Withdrew " + amount + " from account number: " + 
accountNumber);
 } else {
 System.out.println("Account not found or insufficient funds.");
 }
 }
 public void transfer(String fromAccount, String toAccount, double amount) {
 Account from = accounts.get(fromAccount);
 Account to = accounts.get(toAccount);
 if (from != null && to != null) {
 if (from.getBalance() >= amount) {
 from.withdraw(amount);
 to.deposit(amount);
 System.out.println("Transferred " + amount + " from account " + 
fromAccount + " to account " + toAccount);
 } else {
 System.out.println("Insufficient balance in the source account.");
 }
 } else {
 System.out.println("One or both accounts not found.");
 }
 }
 public void applyInterest(String accountNumber) {
 Account account = accounts.get(accountNumber);
 if (account != null) {
 account.applyInterest();
 System.out.println("Interest applied to account number: " + 
accountNumber);
 } else {
 System.out.println("Account not found.");
 }
 }
 public void printAccountDetails(String accountNumber) {
 Account account = accounts.get(accountNumber);
 if (account != null) {
 System.out.println("Account Number: " + account.getAccountNumber());
 System.out.println("Balance: " + account.getBalance());
 System.out.println("Transactions:");
 for (String transaction : account.getTransactions()) {
 System.out.println(" - " + transaction);
 }
 } else {
 System.out.println("Account not found.");
 }
 }
}
Main Class for Simulation
java
public class BankingSystemSimulation {
 public static void main(String[] args) {
 Bank bank = new Bank();
 // Open accounts
 bank.openSavingsAccount("1001", 500.00, 1.5);
 bank.openCheckingAccount("1002", 1000.00);
 // Perform transactions
 bank.deposit("1001", 200.00);
 bank.withdraw("1002", 150.00);
 bank.transfer("1002", "1001", 100.00);
 // Apply interest
 bank.applyInterest("1001");
 // View account details
 bank.printAccountDetails("1001");
 bank.printAccountDetails("1002");
 }
}
