public class Employee {
    private String empId;
    private String name;
    private double basicSalary;
    private double hra;
    private double da;
    private double tax;
    private double netSalary;
    private int totalDays;
    private int presentDays;

    public Employee(String empId, String name, double basicSalary, int totalDays, int presentDays) {
        this.empId = empId;
        this.name = name;
        this.basicSalary = basicSalary;
        this.totalDays = totalDays;
        this.presentDays = presentDays;

        calculateSalary();
    }

    public void calculateSalary() {
        hra = basicSalary * 0.20;
        da = basicSalary * 0.10;
        tax = basicSalary * 0.05;

        double deduction = ((totalDays - presentDays) / (double) totalDays) * basicSalary;
        netSalary = basicSalary + hra + da - tax - deduction;
    }

    public String getEmpId() { return empId; }
    public String getName() { return name; }
    public double getBasicSalary() { return basicSalary; }
    public double getHra() { return hra; }
    public double getDa() { return da; }
    public double getTax() { return tax; }
    public double getNetSalary() { return netSalary; }
    public int getTotalDays() { return totalDays; }
    public int getPresentDays() { return presentDays; }

    @Override
    public String toString() {
        return empId + "," + name + "," + basicSalary + "," + hra + "," + da + "," + tax + "," + netSalary + "," + totalDays + "," + presentDays;
    }
}
