package minibank.repository;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import minibank.annotation.Id;

public class Repository<T> {
    private final Map<String, T> data = new ConcurrentHashMap<>();

    private String getId(T item) {
        try {
            Class<?> type = item.getClass();
            while (type != null) {
                for (Field field : type.getDeclaredFields()) {
                    if (field.isAnnotationPresent(Id.class)) {
                        field.setAccessible(true);
                        return String.valueOf(field.get(item));
                    }
                }
                type = type.getSuperclass();
            }
            throw new IllegalArgumentException("No @Id field found.");
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("Cannot read ID.", e);
        }
    }

    public void save(T item) { data.put(getId(item), item); }
    public T findById(String id) { return data.get(id); }
    public List<T> findAll() { return new ArrayList<>(data.values()); }
    public void delete(String id) { data.remove(id); }
    @SafeVarargs
    public final void saveAll(T... items) {
        for (T item : items) save(item);
    }
    public int size() { return data.size(); }
}
