import java.util.Comparator;

public class Employee {

    //
    private int id;
    private String firstName;
    private String secondName;
    private float payment;

    //Constructs a new Employee instance.
    public Employee(){}

    //Constructs a new Employee instance.
    public Employee(int id, String firstName, String secondName, float payment) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.payment = payment;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPayment() {
        return payment;
    }

    public void setPayment(float payment) {
        this.payment = payment;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public double getSalary(Employee employee) {
        return employee.getPayment();
    }

    public static Comparator<Employee> IdComparator = (o1, o2) -> {
        int id1 = o1.getId();
        int id2 = o2.getId();
        return id1 - id2;
    };

    public static Comparator<Employee> FirstNameComparator = (o1, o2) -> {
        String firstName1 = o1.getFirstName().toUpperCase();
        String firstName2 = o2.getFirstName().toUpperCase();
        return firstName1.compareTo(firstName2);
    };

    public static Comparator<Employee> SecondNameComparator = (o1, o2) -> {
        String secondName1 = o1.getSecondName().toUpperCase();
        String secondName2 = o2.getSecondName().toUpperCase();
        return secondName1.compareTo(secondName2);
    };

    public static Comparator<Employee> PaymentComparator = (o1, o2) -> {
        Double diff = o1.getSalary(o1) - o2.getSalary(o2);
        return diff.intValue();
    };

    public static String toString(Employee employee){

        return String.format(
                "%s %s;  ID = %s;  %s UAH",
                employee.getFirstName(),
                employee.getSecondName(),
                employee.getId(),
                employee.getSalary(employee));
    }
}

