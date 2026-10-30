
import java.lang.annotation.*;
import java.lang.reflect.Field;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Column {
    String name();
}

class Student {

    @Column(name = "student_name")
    String name;

    @Column(name = "student_age")
    int age;

    public String toString() {
        return "Name: " + name + ", Age: " + age;
    }
}

public class ColumnMapper {

    public static void main(String[] args) throws Exception {
        String[] header = {"student_age", "student_name"};
        String[] data = {"20", "Rahul"};

        Student student = new Student();
        Field[] fields = Student.class.getDeclaredFields();

        for (Field field : fields) {
            Column column = field.getAnnotation(Column.class);

            if (column != null) {
                int index = -1;

                for (int i = 0; i < header.length; i++) {
                    if (header[i].equals(column.name())) {
                        index = i;
                        break;
                    }
                }

                if (index == -1) {
                    System.out.println("Missing column: " + column.name());
                    continue;
                }

                field.setAccessible(true);

                if (field.getType() == String.class) {
                    field.set(student, data[index]);
                } else if (field.getType() == int.class) {
                    field.setInt(student, Integer.parseInt(data[index]));
                }
            }
        }

        System.out.println(student);
    }
}
