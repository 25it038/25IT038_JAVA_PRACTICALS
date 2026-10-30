
import java.lang.annotation.*;
import java.lang.reflect.Method;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Run {
}

class MyTests {

    @Run
    public void testAddition() {
        System.out.println("Addition test passed");
    }

    @Run
    public void testSubtraction() {
        System.out.println("Subtraction test passed");
    }

    public void normalMethod() {
        System.out.println("This method will not run");
    }
}

public class MiniTestRunner {

    public static void main(String[] args) throws Exception {
        MyTests testObject = new MyTests();
        Method[] methods = MyTests.class.getDeclaredMethods();
        int count = 0;

        for (Method method : methods) {
            if (method.isAnnotationPresent(Run.class)) {
                method.invoke(testObject);
                count++;
            }
        }

        System.out.println("Total methods executed: " + count);
    }
}
