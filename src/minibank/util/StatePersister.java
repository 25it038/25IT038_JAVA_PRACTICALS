package minibank.util;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import minibank.model.Account;

public class StatePersister {
    private static final Path FILE = Paths.get("data", "accounts.dat");

    public static void save(List<Account> accounts) throws IOException {
        Files.createDirectories(FILE.getParent());
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(FILE))) {
            out.writeObject(new ArrayList<>(accounts));
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Account> load() throws IOException, ClassNotFoundException {
        if (!Files.exists(FILE)) return new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(FILE))) {
            return (List<Account>) in.readObject();
        }
    }
}
