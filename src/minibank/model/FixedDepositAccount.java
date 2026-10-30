package minibank.model;
public class FixedDepositAccount extends Account {
    private static final long serialVersionUID = 1L;
    public FixedDepositAccount(String number, Customer customer, double balance) {
        super(number, customer, balance);
    }
    @Override
    public boolean canWithdraw(double amount) { return false; }
    @Override
    public String getAccountType() { return "Fixed Deposit (no early withdrawal)"; }
}
