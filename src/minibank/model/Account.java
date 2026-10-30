package minibank.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import minibank.annotation.Id;
import minibank.annotation.Positive;
import minibank.exception.*;

public abstract class Account implements Serializable, Comparable<Account> {
    private static final long serialVersionUID = 1L;
    @Id
    private String accountNumber;
    private Customer customer;
    @Positive
    protected double balance;
    private List<String> transactions = new ArrayList<>();

    public Account(String accountNumber, Customer customer, double openingBalance) {
        this.accountNumber = accountNumber;
        this.customer = customer;
        this.balance = openingBalance;
        transactions.add("Account opened with Rs. " + openingBalance);
    }

    public String getAccountNumber() { return accountNumber; }
    public Customer getCustomer() { return customer; }
    public synchronized double getBalance() { return balance; }
    public synchronized List<String> getTransactions() { return new ArrayList<>(transactions); }

    public synchronized void deposit(double amount) throws InvalidAmountException {
        if (amount <= 0) throw new InvalidAmountException("Amount must be greater than zero.");
        balance += amount;
        transactions.add("Deposited Rs. " + amount);
    }

    public synchronized void withdraw(double amount)
            throws InvalidAmountException, InsufficientFundsException {
        if (amount <= 0) throw new InvalidAmountException("Amount must be greater than zero.");
        if (!canWithdraw(amount)) throw new InsufficientFundsException("Withdrawal not allowed or balance is insufficient.");
        balance -= amount;
        transactions.add("Withdrawn Rs. " + amount);
    }

    public abstract boolean canWithdraw(double amount);
    public abstract String getAccountType();

    public synchronized void addTransaction(String message) { transactions.add(message); }

    @Override
    public int compareTo(Account other) {
        return Double.compare(this.getBalance(), other.getBalance());
    }

    @Override
    public String toString() {
        return accountNumber + " | " + getAccountType() + " | " + customer.getName()
                + " | Balance: Rs. " + getBalance();
    }
}
