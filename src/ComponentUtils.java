import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ComponentUtils {
    // Utility method to create a placeholder label
    public static JLabel createPlaceholderLabel(String text, int x, int y) {
        JLabel placeholder = new JLabel(text);
        placeholder.setFont(new Font("Arial", Font.PLAIN, 15));
        placeholder.setForeground(Color.GRAY);
        placeholder.setBounds(x, y, 150, 20);
        return placeholder;
    }

    // Utility method to create a text field
    public static JTextField createTextField(int x, int y) {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 15));
        textField.setSize(150, 20);
        textField.setLocation(x, y);
        return textField;
    }

    // Utility method to create a password field
    public static JPasswordField createPasswordField(int x, int y) {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 15));
        passwordField.setSize(150, 20);
        passwordField.setLocation(x, y);
        return passwordField;
    }

    // Utility method to create the show password button
    public static JButton createShowPasswordButton(ActionListener listener) {
        JButton showPasswordButton = new JButton(new ImageIcon("hide_pass_icon.png"));
        showPasswordButton.setSize(19, 19);
        showPasswordButton.setBorder(BorderFactory.createEmptyBorder());
        showPasswordButton.setContentAreaFilled(false);
        showPasswordButton.setOpaque(false);
        showPasswordButton.setLocation(330, 160);
        showPasswordButton.setFocusPainted(false); // Avoid painting the button focus state
        showPasswordButton.setFocusTraversalKeysEnabled(false);
        showPasswordButton.addActionListener(listener); // Attach the listener
        return showPasswordButton;
    }

    public static JButton createBackToLoginButton(ActionListener listener) {
        JButton backToLoginButton = new JButton("Back to Login");
        backToLoginButton.setFont(new Font("Arial", Font.PLAIN, 15));
        backToLoginButton.setSize(150, 20);
        backToLoginButton.setLocation(175, 235); // You can adjust the location as needed
        backToLoginButton.setBorder(BorderFactory.createEmptyBorder());
        backToLoginButton.setContentAreaFilled(false);
        backToLoginButton.setOpaque(true);
        backToLoginButton.setBackground(Color.ORANGE);
        backToLoginButton.setForeground(Color.WHITE);
        backToLoginButton.setFocusTraversalKeysEnabled(false);
        backToLoginButton.setFocusPainted(false);
        backToLoginButton.addActionListener(listener); // Attach the listener
        return backToLoginButton;
    }

    public static JButton createLoginButton(int x, int y, int width, int height, ActionListener listener) {
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.PLAIN, 15));
        loginButton.setSize(width, height);
        loginButton.setLocation(x, y);
        loginButton.setBorder(BorderFactory.createEmptyBorder());
        loginButton.setContentAreaFilled(false);
        loginButton.setOpaque(true);
        loginButton.setBackground(Color.ORANGE);
        loginButton.setForeground(Color.WHITE);
        loginButton.addActionListener(listener);
        loginButton.setFocusTraversalKeysEnabled(false);
        loginButton.setFocusPainted(false);
        return loginButton;
    }

    public static JButton createCreateAccountButton(int x, int y, int width, int height, ActionListener listener) {
        JButton createAccButton = new JButton("Create Account");
        createAccButton.setFont(new Font("Arial", Font.PLAIN, 15));
        createAccButton.setSize(width, height);
        createAccButton.setLocation(x, y);
        createAccButton.setBorder(BorderFactory.createEmptyBorder());
        createAccButton.setContentAreaFilled(false);
        createAccButton.setOpaque(true);
        createAccButton.setBackground(Color.ORANGE);
        createAccButton.setForeground(Color.WHITE);
        createAccButton.addActionListener(listener);
        createAccButton.setFocusTraversalKeysEnabled(false);
        createAccButton.setFocusPainted(false);
        return createAccButton;
    }

    public static JButton createCreateUserAccountButton(int x, int y, int width, int height, ActionListener listener) {
        JButton createUserAccButton = new JButton("Create Account");
        createUserAccButton.setFont(new Font("Arial", Font.PLAIN, 15));
        createUserAccButton.setSize(width, height);
        createUserAccButton.setLocation(x, y);
        createUserAccButton.setBorder(BorderFactory.createEmptyBorder());
        createUserAccButton.setContentAreaFilled(false);
        createUserAccButton.setOpaque(true);
        createUserAccButton.setBackground(Color.ORANGE);
        createUserAccButton.setForeground(Color.WHITE);
        createUserAccButton.addActionListener(listener);
        createUserAccButton.setFocusTraversalKeysEnabled(false);
        createUserAccButton.setFocusPainted(false);
        return createUserAccButton;
    }
}
