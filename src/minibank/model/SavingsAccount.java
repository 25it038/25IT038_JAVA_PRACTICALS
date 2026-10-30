package minibank.model;
public class SavingsAccount extends Account {
    private static final long serialVersionUID = 1L;
    public SavingsAccount(String number, Customer customer, double balance) {
        super(number, customer, balance);
    }
    @Override
    public boolean canWithdraw(double amount) { return amount <= getBalance(); }
    @Override
    public String getAccountType() { return "Savings"; }
}
