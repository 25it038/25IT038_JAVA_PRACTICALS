package minibank.util;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import minibank.model.Transaction;

public class TransactionLog {
    public static void save(List<Transaction> transactions) throws IOException {
        Path file = Paths.get("data", "transactions.txt");
        Files.createDirectories(file.getParent());
        StringBuilder text = new StringBuilder();
        for (Transaction transaction : transactions) text.append(transaction).append(System.lineSeparator());
        Files.writeString(file, text.toString(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
}
