package minibank;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import minibank.model.*;
import minibank.service.*;
import minibank.util.*;
import minibank.exception.*;

public class MiniBank {
    private static final Scanner sc = new Scanner(System.in);
    private static final BankService bank = new BankService();

    public static void main(String[] args) {
        try {
            bank.replaceAll(StatePersister.load());
            System.out.println("Welcome to MiniBank!");
            boolean running = true;
            while (running) {
                showMenu();
                int choice = readInt("Enter choice: ");
                try {
                    switch (choice) {
                        case 1 -> openAccount();
                        case 2 -> viewAccount();
                        case 3 -> deposit();
                        case 4 -> withdraw();
                        case 5 -> transfer();
                        case 6 -> showAllAccounts();
                        case 7 -> showStatement();
                        case 8 -> runThreadDemo();
                        case 9 -> {
                            saveData();
                            running = false;
                            System.out.println("Thank you for using MiniBank!");
                        }
                        default -> System.out.println("Please enter a choice from 1 to 9.");
                    }
                } catch (BankException | IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("Could not load saved data: " + e.getMessage());
        }
    }

    private static void showMenu() {
        System.out.println("\\n========== MINIBANK ==========");
        System.out.println("1. Open New Account");
        System.out.println("2. View Account Details");
        System.out.println("3. Deposit Money");
        System.out.println("4. Withdraw Money");
        System.out.println("5. Transfer Money");
        System.out.println("6. List All Accounts");
        System.out.println("7. View Transaction History");
        System.out.println("8. Thread Safety Demo");
        System.out.println("9. Save and Exit");
    }

    private static void openAccount() {
        System.out.print("Customer name: ");
        String name = sc.nextLine().trim();
        System.out.print("Phone: ");
        String phone = sc.nextLine().trim();
        System.out.print("Address: ");
        String address = sc.nextLine().trim();
        System.out.print("Account number: ");
        String number = sc.nextLine().trim();
        System.out.println("Account type: 1. Savings  2. Current  3. Fixed Deposit");
        int type = readInt("Choose type: ");
        double amount = readDouble("Opening balance: ");
        if (amount < 0) throw new IllegalArgumentException("Opening balance cannot be negative.");
        Customer customer = new Customer(name, phone, address);
        Account account;
        switch (type) {
            case 1 -> account = new SavingsAccount(number, customer, amount);
            case 2 -> account = new CurrentAccount(number, customer, amount);
            case 3 -> account = new FixedDepositAccount(number, customer, amount);
            default -> throw new IllegalArgumentException("Invalid account type.");
        }
        List<String> errors = AnnotationValidator.validate(account);
        if (!errors.isEmpty()) throw new IllegalArgumentException(String.join(", ", errors));
        bank.openAccount(account);
        System.out.println("Account created successfully.");
    }

    private static void viewAccount() throws BankException {
        Account account = bank.findAccount(readLine("Enter account number: "));
        System.out.println(account);
        System.out.println(account.getCustomer());
    }

    private static void deposit() throws BankException {
        bank.deposit(readLine("Account number: "), readDouble("Amount to deposit: "));
        System.out.println("Deposit successful.");
    }

    private static void withdraw() throws BankException {
        bank.withdraw(readLine("Account number: "), readDouble("Amount to withdraw: "));
        System.out.println("Withdrawal successful.");
    }

    private static void transfer() throws BankException {
        String from = readLine("From account: ");
        String to = readLine("To account: ");
        double amount = readDouble("Amount: ");
        bank.transfer(from, to, amount);
        System.out.println("Transfer successful.");
    }

    private static void showAllAccounts() {
        List<Account> list = bank.getAllAccounts();
        if (list.isEmpty()) System.out.println("No accounts found.");
        else list.stream().sorted().forEach(System.out::println);
    }

    private static void showStatement() {
        List<Transaction> list = bank.getHistory();
        if (list.isEmpty()) System.out.println("No transactions found.");
        else list.forEach(System.out::println);
    }

    private static void runThreadDemo() throws BankException {
        List<Account> list = bank.getAllAccounts();
        if (list.isEmpty()) {
            System.out.println("Create an account first.");
            return;
        }
        Account account = list.get(0);
        ExecutorService pool = Executors.newFixedThreadPool(3);
        for (int i = 1; i <= 5; i++) pool.submit(new AccountWorker(account, 10));
        pool.shutdown();
        try {
            if (pool.awaitTermination(5, TimeUnit.SECONDS))
                System.out.println("All deposit tasks completed. New balance: Rs. " + account.getBalance());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread was interrupted.");
        }
    }

    private static void saveData() {
        try {
            StatePersister.save(bank.getAllAccounts());
            TransactionLog.save(bank.getHistory());
            System.out.println("Account data and transaction log saved.");
        } catch (IOException e) {
            System.out.println("Could not save data: " + e.getMessage());
        }
    }

    private static String readLine(String message) {
        System.out.print(message);
        return sc.nextLine().trim();
    }
    private static int readInt(String message) {
        while (true) {
            try { return Integer.parseInt(readLine(message)); }
            catch (NumberFormatException e) { System.out.println("Enter a valid whole number."); }
        }
    }
    private static double readDouble(String message) {
        while (true) {
            try { return Double.parseDouble(readLine(message)); }
            catch (NumberFormatException e) { System.out.println("Enter a valid amount."); }
        }
    }
}
