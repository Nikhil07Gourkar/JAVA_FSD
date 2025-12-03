import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    public LoginFrame() {
        setTitle("Login");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2, 10, 10));

        JLabel userLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");

        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();

        JButton loginBtn = new JButton("Login");

        add(userLabel);
        add(userField);
        add(passLabel);
        add(passField);
        add(new JLabel());
        add(loginBtn);

        loginBtn.addActionListener(e -> {
            String user = userField.getText();
            String pass = new String(passField.getPassword());

            if (user.equals("admin") && pass.equals("admin")) {
                new PayrollApp().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password!");
            }
        });
    }
}
