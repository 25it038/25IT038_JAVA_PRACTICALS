package minibank.model;
import java.io.Serializable;
import java.time.LocalDateTime;
public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String details;
    private final LocalDateTime time;
    public Transaction(String details) {
        this.details = details;
        this.time = LocalDateTime.now();
    }
    @Override
    public String toString() { return time + " - " + details; }
}
