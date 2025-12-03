import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PayrollApp extends JFrame {

    private JTextField idField, nameField, salaryField, totalDaysField, presentDaysField;
    private DefaultTableModel tableModel;

    public PayrollApp() {
        setTitle("Employee Payroll System");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();

        // Employee Registration Tab
        JPanel regPanel = new JPanel(new GridLayout(6,2,10,10));
        idField = new JTextField();
        nameField = new JTextField();
        salaryField = new JTextField();
        totalDaysField = new JTextField();
        presentDaysField = new JTextField();
        JButton addBtn = new JButton("Add Employee");

        regPanel.add(new JLabel("Employee ID:")); regPanel.add(idField);
        regPanel.add(new JLabel("Name:")); regPanel.add(nameField);
        regPanel.add(new JLabel("Basic Salary:")); regPanel.add(salaryField);
        regPanel.add(new JLabel("Total Days:")); regPanel.add(totalDaysField);
        regPanel.add(new JLabel("Present Days:")); regPanel.add(presentDaysField);
        regPanel.add(new JLabel()); regPanel.add(addBtn);

        addBtn.addActionListener(e -> addEmployee());

        tabs.add("Employee Registration", regPanel);

        // Employee List Tab
        JPanel listPanel = new JPanel(new BorderLayout());
        tableModel = new DefaultTableModel(
            new String[]{"ID","Name","Basic","HRA","DA","Tax","Net Salary","Total Days","Present Days"},0
        );
        JTable table = new JTable(tableModel);
        loadAllEmployees();
        listPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        tabs.add("Employee List", listPanel);

        // Search Tab
        JPanel searchPanel = new JPanel(new GridLayout(3,2,10,10));
        JTextField searchField = new JTextField();
        JButton searchBtn = new JButton("Search");
        JTextArea searchResult = new JTextArea();
        searchResult.setEditable(false);

        searchPanel.add(new JLabel("Enter Employee ID or Name:")); searchPanel.add(searchField);
        searchPanel.add(searchBtn); searchPanel.add(new JLabel());
        searchPanel.add(new JLabel("Result:")); searchPanel.add(searchResult);

        searchBtn.addActionListener(e -> {
            String query = searchField.getText().trim().toLowerCase();
            List<Employee> list = FileStorage.loadEmployees();
            StringBuilder result = new StringBuilder();
            for(Employee emp : list){
                if(emp.getEmpId().toLowerCase().equals(query) || emp.getName().toLowerCase().equals(query)){
                    result.append("ID: ").append(emp.getEmpId())
                          .append("\nName: ").append(emp.getName())
                          .append("\nNet Salary: ").append(emp.getNetSalary())
                          .append("\nAttendance: ").append(emp.getPresentDays()).append("/").append(emp.getTotalDays())
                          .append("\n-------------------\n");
                }
            }
            if(result.length()==0) result.append("No employee found.");
            searchResult.setText(result.toString());
        });

        tabs.add("Search Employee", searchPanel);

        // Payslip Tab
        JPanel payslipPanel = new JPanel(new GridLayout(3,2,10,10));
        JTextField payslipId = new JTextField();
        JButton generateBtn = new JButton("Generate Payslip");
        JTextArea payslipArea = new JTextArea();
        payslipArea.setEditable(false);

        payslipPanel.add(new JLabel("Enter Employee ID:")); payslipPanel.add(payslipId);
        payslipPanel.add(generateBtn); payslipPanel.add(new JLabel());
        payslipPanel.add(new JLabel("Payslip:")); payslipPanel.add(new JScrollPane(payslipArea));

        generateBtn.addActionListener(e -> {
            String id = payslipId.getText().trim();
            List<Employee> list = FileStorage.loadEmployees();
            for(Employee emp : list){
                if(emp.getEmpId().equalsIgnoreCase(id)){
                    String slip = "------ PAYSLIP ------\n";
                    slip += "Employee ID: "+emp.getEmpId()+"\n";
                    slip += "Name: "+emp.getName()+"\n";
                    slip += "Basic Salary: "+emp.getBasicSalary()+"\n";
                    slip += "HRA: "+emp.getHra()+"\n";
                    slip += "DA: "+emp.getDa()+"\n";
                    slip += "Tax: "+emp.getTax()+"\n";
                    slip += "Attendance: "+emp.getPresentDays()+"/"+emp.getTotalDays()+"\n";
                    slip += "Net Salary: "+emp.getNetSalary()+"\n";
                    slip += "-------------------\n";
                    payslipArea.setText(slip);
                    return;
                }
            }
            payslipArea.setText("Employee not found.");
        });

        tabs.add("Payslip", payslipPanel);

        add(tabs);
    }

    private void addEmployee() {
        try{
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            double basic = Double.parseDouble(salaryField.getText().trim());
            int totalDays = Integer.parseInt(totalDaysField.getText().trim());
            int presentDays = Integer.parseInt(presentDaysField.getText().trim());

            Employee emp = new Employee(id,name,basic,totalDays,presentDays);
            FileStorage.saveEmployee(emp);

            tableModel.addRow(new Object[]{
                emp.getEmpId(), emp.getName(), emp.getBasicSalary(),
                emp.getHra(), emp.getDa(), emp.getTax(),
                emp.getNetSalary(), emp.getTotalDays(), emp.getPresentDays()
            });

            JOptionPane.showMessageDialog(this,"Employee Added Successfully!");

        } catch(Exception ex){
            JOptionPane.showMessageDialog(this,"Invalid Input!");
        }
    }

    private void loadAllEmployees(){
        List<Employee> list = FileStorage.loadEmployees();
        for(Employee emp : list){
            tableModel.addRow(new Object[]{
                emp.getEmpId(), emp.getName(), emp.getBasicSalary(),
                emp.getHra(), emp.getDa(), emp.getTax(),
                emp.getNetSalary(), emp.getTotalDays(), emp.getPresentDays()
            });
        }
    }
}
