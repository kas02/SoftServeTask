public class EmployeeTax extends Employee{

    public EmployeeTax(){}

    public EmployeeTax(int id, String firstName, String secondName, float payment) {
        super(id, firstName, secondName, payment);
    }

    @Override
    public double getSalary(Employee employee) {
        return super.getSalary(employee)*20.8*8;
    }
}