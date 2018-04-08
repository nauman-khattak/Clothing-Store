package clothes.store;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import static javax.swing.JFrame.setDefaultLookAndFeelDecorated;

public class Login extends JFrame {

    JButton loginButton;
    JTextField uNameTxtField;
    JPasswordField passwdField;

    public Login(){
        initializeUI();

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!uNameTxtField.getText().trim().equals("") && passwdField.getPassword().length != 0) {
                    if (uNameTxtField.getText().equalsIgnoreCase("hudayjah") && new String(passwdField.getPassword()).equals("hudayjah")) {
                        setVisible(false);
                        JOptionPane.showMessageDialog(null, "Welcome " + uNameTxtField.getText(), "Successfully Logged In", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                        try {
                            new Display_Store_Details();
                        } catch (SQLException ex) {
                            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Wrong Username or Password", "Eroor", JOptionPane.ERROR_MESSAGE);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Fill all the TextFields", "Error", JOptionPane.ERROR_MESSAGE);
                }

            }

        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

    }

    public void initializeUI() {
        setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginButton = new JButton("Login");
        JLabel lb = new JLabel("Username");
        lb.setFont(new Font("Arial", Font.BOLD, 15));
        JLabel lb2 = new JLabel("Password");
        lb2.setFont(new Font("Arial", Font.BOLD, 15));
        lb.setForeground(new Color(0, 96, 169));
        lb2.setForeground(new Color(0, 96, 169));
        uNameTxtField = new JTextField();
        passwdField = new JPasswordField(10);
        setTitle("Login User");
        setSize(900, 700);
        setLocation(250, 20);
        setLayout(null);
        uNameTxtField.setBounds(340, 260, 200, 30);
        uNameTxtField.setVisible(true);
        passwdField.setBounds(340, 320, 200, 30);
        passwdField.setVisible(true);
        lb.setBounds(240, 230, 100, 100);
        lb.setVisible(true);
        lb2.setBounds(240, 280, 100, 100);
        lb2.setVisible(true);
        Font f = new Font("SansSherif", Font.BOLD, 20);
        loginButton.setBounds(390, 380, 100, 40);
        loginButton.setVisible(true);
        loginButton.setBackground(new Color(0, 96, 169));
        loginButton.setFont(new Font("Arial", Font.BOLD, 15));
        URL url = getClass().getResource("login-system-icon-13.png");
        ImageIcon imageIcon = new ImageIcon(url.getPath());
        JLabel lb3 = new JLabel(imageIcon);
        setBackground(Color.WHITE);
        lb3.setBounds(280, 2, 300, 200);
        add(lb);
        add(uNameTxtField);
        add(lb2);
        add(passwdField);
        add(loginButton);
        add(lb3);
        setVisible(true);
    }
}
