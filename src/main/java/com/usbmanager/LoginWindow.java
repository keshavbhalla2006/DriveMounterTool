package com.usbmanager;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.util.Properties;

public class LoginWindow extends JFrame {
    public LoginWindow() {
        setTitle("USB Manager Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();
        JButton loginBtn = new JButton("Login");

        loginBtn.addActionListener(e -> {
            try {
                Properties props = new Properties();
                props.load(new FileInputStream("src/main/resources/users.properties"));
                String username = userField.getText();
                String password = new String(passField.getPassword());
                if (props.containsKey(username) && props.getProperty(username).equals(password)) {
                    boolean isAdmin = username.equals("admin");
                    dispose();
                    new MainWindow(isAdmin);
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid login");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        setLayout(new GridLayout(3, 2));
        add(userLabel); add(userField);
        add(passLabel); add(passField);
        add(new JLabel()); add(loginBtn);
        setVisible(true);
    }
}
