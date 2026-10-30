package minibank.service;

import minibank.model.Account;

public class AccountWorker implements Runnable {
    private final Account account;
    private final double amount;
    public AccountWorker(Account account, double amount) {
        this.account = account;
        this.amount = amount;
    }
    @Override
    public void run() {
        try {
            account.deposit(amount);
            System.out.println(Thread.currentThread().getName() + " deposited Rs. " + amount);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
