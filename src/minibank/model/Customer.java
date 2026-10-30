package minibank.model;
import java.io.Serializable;
import minibank.annotation.MaxLength;

public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    @MaxLength(40)
    private String name;
    private String phone;
    private String address;

    public Customer(String name, String phone, String address) {
        this.name = name;
        this.phone = phone;
        this.address = address;
    }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    @Override
    public String toString() {
        return "Name: " + name + " | Phone: " + phone + " | Address: " + address;
    }
}
