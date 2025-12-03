import java.io.*;
import java.util.*;

public class FileStorage {
    private static final String FILE_NAME = "employees.txt";

    public static void saveEmployee(Employee emp) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            bw.write(emp.toString());
            bw.newLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Employee> loadEmployees() {
        List<Employee> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                Employee emp = new Employee(
                    data[0], data[1], Double.parseDouble(data[2]), 
                    Integer.parseInt(data[7]), Integer.parseInt(data[8])
                );
                list.add(emp);
            }
        } catch (Exception e) {
            System.out.println("No employees found.");
        }
        return list;
    }
}
