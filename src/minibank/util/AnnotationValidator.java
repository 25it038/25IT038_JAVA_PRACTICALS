package minibank.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import minibank.annotation.*;

public class AnnotationValidator {
    public static List<String> validate(Object object) {
        List<String> errors = new ArrayList<>();
        Class<?> type = object.getClass();
        while (type != null) {
            for (Field field : type.getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    Object value = field.get(object);
                    if (field.isAnnotationPresent(Positive.class) && value instanceof Number
                            && ((Number) value).doubleValue() < 0) {
                        errors.add(field.getName() + " cannot be negative.");
                    }
                    if (field.isAnnotationPresent(MaxLength.class) && value instanceof String) {
                        int max = field.getAnnotation(MaxLength.class).value();
                        if (((String) value).length() > max)
                            errors.add(field.getName() + " must be at most " + max + " characters.");
                    }
                } catch (IllegalAccessException e) {
                    errors.add("Could not validate " + field.getName());
                }
            }
            type = type.getSuperclass();
        }
        return errors;
    }
}
