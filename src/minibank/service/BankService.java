package minibank.service;

import java.util.*;
import minibank.model.*;
import minibank.repository.Repository;
import minibank.exception.*;

public class BankService {
    private final Repository<Account> accounts = new Repository<>();
    private final List<Transaction> history = Collections.synchronizedList(new ArrayList<>());

    public void openAccount(Account account) {
        if (accounts.findById(account.getAccountNumber()) != null)
            throw new IllegalArgumentException("Account number already exists.");
        accounts.save(account);
        history.add(new Transaction("Opened " + account.getAccountType() + " account " + account.getAccountNumber()));
    }

    public Account findAccount(String number) throws AccountNotFoundException {
        Account account = accounts.findById(number);
        if (account == null) throw new AccountNotFoundException("Account not found.");
        return account;
    }

    public void deposit(String number, double amount) throws BankException {
        Account account = findAccount(number);
        account.deposit(amount);
        history.add(new Transaction("Rs. " + amount + " deposited into " + number));
    }

    public void withdraw(String number, double amount) throws BankException {
        Account account = findAccount(number);
        account.withdraw(amount);
        history.add(new Transaction("Rs. " + amount + " withdrawn from " + number));
    }

    public void transfer(String from, String to, double amount) throws BankException {
        Account source = findAccount(from);
        Account target = findAccount(to);
        if (source == target) throw new InvalidAmountException("Source and destination must be different.");
        Account first = from.compareTo(to) < 0 ? source : target;
        Account second = first == source ? target : source;
        synchronized (first) {
            synchronized (second) {
                source.withdraw(amount);
                try {
                    target.deposit(amount);
                } catch (BankException e) {
                    source.deposit(amount);
                    throw e;
                }
            }
        }
        history.add(new Transaction("Transferred Rs. " + amount + " from " + from + " to " + to));
    }

    public List<Account> getAllAccounts() { return accounts.findAll(); }
    public List<Transaction> getHistory() { synchronized (history) { return new ArrayList<>(history); } }
    public int accountCount() { return accounts.size(); }

    public void replaceAll(List<Account> loaded) {
        for (Account account : loaded) accounts.save(account);
    }
}
