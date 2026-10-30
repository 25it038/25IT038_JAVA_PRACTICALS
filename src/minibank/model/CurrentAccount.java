package minibank.model;
public class CurrentAccount extends Account {
    private static final long serialVersionUID = 1L;
    private static final double OVERDRAFT_LIMIT = 5000;
    public CurrentAccount(String number, Customer customer, double balance) {
        super(number, customer, balance);
    }
    @Override
    public boolean canWithdraw(double amount) { return amount <= getBalance() + OVERDRAFT_LIMIT; }
    @Override
    public String getAccountType() { return "Current"; }
}
