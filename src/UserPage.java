import javax.swing.*;
import javax.swing.text.Caret;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

public class UserPage extends JFrame implements ActionListener {
    private LoginBackgroundPanel c;
    private JTextField tusername;
    private JPasswordField tpassword;
    private JPasswordField tconfirmpassword;
    private JButton login;
    private JButton createAcc;
    private JButton reset;
    private JButton showPasswordButton;
    private boolean passwordVisible = false;
    private JLabel usernamePlaceholder;
    private JLabel passwordPlaceholder;
    private JLabel confirmPasswordPlaceholder;

    private JButton createUserAcc;
    private JButton backToLogin;

    // Constructor, to initialize the components with default values.
    public UserPage() {

        // Initialize the JFrame properties
        setTitle("User Page");
        setBounds(300, 90, 500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        // Initialize the BackgroundPanel
        c = new LoginBackgroundPanel("login_screen_background.png");
        c.setLayout(null);
        setContentPane(c); // Use the BackgroundPanel as the content pane

        // Initialize UI components
        initializeComponents();

        // Setup UI for login by default or directly call the method you wish to start with
        setupLoginUI();

        // Make JFrame visible
        setVisible(true);
    }

    private void initializeComponents() {
        // Initialize placeholders
        usernamePlaceholder = ComponentUtils.createPlaceholderLabel("Username", 175, 135);
        passwordPlaceholder = ComponentUtils.createPlaceholderLabel("Password", 175, 160);
        confirmPasswordPlaceholder = ComponentUtils.createPlaceholderLabel("Confirm Password", 175, 185); // Only for account creation

        // Initialize text fields
        tusername = ComponentUtils.createTextField(175, 135);
        tpassword = ComponentUtils.createPasswordField(175, 160);
        tconfirmpassword = ComponentUtils.createPasswordField(175, 185); // Only for account creation

        // Initialize buttons
        login = ComponentUtils.createLoginButton(175, 220, 150, 20, this::actionPerformed);
        createAcc = ComponentUtils.createCreateAccountButton(175, 245, 150, 20, this::actionPerformed);
        createUserAcc = ComponentUtils.createCreateUserAccountButton(175, 210, 150, 20, this::actionPerformed); // Only for account creation
        backToLogin = ComponentUtils.createBackToLoginButton(this); // Only for account creation
        showPasswordButton = ComponentUtils.createShowPasswordButton(this);
    }

    private void setupLoginUI() {
        // Clear all components first
        c.removeAll();

        // Create and add placeholders
        usernamePlaceholder = ComponentUtils.createPlaceholderLabel("Username", 175, 135);
        passwordPlaceholder = ComponentUtils.createPlaceholderLabel("Password", 175, 160);

        // Create and add text fields
        tusername = ComponentUtils.createTextField(175, 135);
        tpassword = ComponentUtils.createPasswordField(175, 160);

        // Create and add buttons
        login = ComponentUtils.createLoginButton(175, 220, 150, 20, this::actionPerformed);
        createAcc = ComponentUtils.createCreateAccountButton(175, 245, 150, 20, this::actionPerformed);
        showPasswordButton = ComponentUtils.createShowPasswordButton(this);

        // Adding components to the container
        c.add(usernamePlaceholder);
        c.add(passwordPlaceholder);
        c.add(tusername);
        c.add(tpassword);
        c.add(login);
        c.add(createAcc);
        c.add(showPasswordButton);

        setupPlaceholderListeners(); // This will attach listeners to the components

        UIUtilities.focusComponentsInSequence(tusername, tpassword); // Focus components and un focus

        // Refresh the panel
        c.revalidate();
        c.repaint();
    }

    private void setupCreateAccountUI() {
        // Clear all components first
        c.removeAll();

        // Create and add placeholders
        usernamePlaceholder = ComponentUtils.createPlaceholderLabel("Enter Username", 175, 135);
        passwordPlaceholder = ComponentUtils.createPlaceholderLabel("Enter Password", 175, 160);
        confirmPasswordPlaceholder = ComponentUtils.createPlaceholderLabel("Confirm Password", 175, 185);

        // Create and add text fields
        tusername = ComponentUtils.createTextField(175, 135);
        tpassword = ComponentUtils.createPasswordField(175, 160);
        tconfirmpassword = ComponentUtils.createPasswordField(175, 185);

        // Create and add buttons
        createUserAcc = ComponentUtils.createCreateUserAccountButton(175, 210, 150, 20, this::actionPerformed);
        backToLogin = ComponentUtils.createBackToLoginButton(this);
        showPasswordButton = ComponentUtils.createShowPasswordButton(this);

        // Adding components to the container
        c.add(usernamePlaceholder);
        c.add(passwordPlaceholder);
        c.add(confirmPasswordPlaceholder);
        c.add(tusername);
        c.add(tpassword);
        c.add(tconfirmpassword);
        c.add(createUserAcc);
        c.add(backToLogin);
        c.add(showPasswordButton);

        setupPlaceholderListeners(); // This will attach listeners to the components

        UIUtilities.focusComponentsInSequence(tusername, tpassword, tconfirmpassword); // Focus and un focus

        // Refresh the panel
        c.revalidate();
        c.repaint();
    }

    private void setupPlaceholderListeners() {
        tusername.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                // Hide placeholder and show caret
                usernamePlaceholder.setVisible(false);
                Caret caret = tusername.getCaret();
                caret.setVisible(true);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (tusername.getText().isEmpty()) {
                    // Show placeholder and hide caret
                    usernamePlaceholder.setVisible(true);
                    Caret caret = tusername.getCaret();
                    caret.setVisible(false);
                }
            }
        });

        tusername.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                boolean isEmpty = tusername.getText().isEmpty();
                usernamePlaceholder.setVisible(isEmpty);

                // Hide or show the caret based on text presence
                Caret caret = tusername.getCaret();
                caret.setVisible(!isEmpty);
            }
        });

        tpassword.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                passwordPlaceholder.setVisible(false);
                Caret caret = tpassword.getCaret();
                caret.setVisible(true);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (tpassword.getPassword().length == 0) {
                    passwordPlaceholder.setVisible(true);
                    Caret caret = tpassword.getCaret();
                    caret.setVisible(false);
                }
            }
        });

        tpassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                boolean isEmpty = tpassword.getPassword().length == 0;
                passwordPlaceholder.setVisible(isEmpty);

                // Hide or show the caret based on text presence
                Caret caret = tpassword.getCaret();
                caret.setVisible(!isEmpty);
            }
        });

        // Add focus listener for tconfirmpassword
        tconfirmpassword.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                confirmPasswordPlaceholder.setVisible(false);
                Caret caret = tconfirmpassword.getCaret();
                caret.setVisible(true);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (tconfirmpassword.getPassword().length == 0) {
                    confirmPasswordPlaceholder.setVisible(true);
                    Caret caret = tconfirmpassword.getCaret();
                    caret.setVisible(false);
                }
            }
        });

        // Add key listener for tconfirmpassword
        tconfirmpassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                boolean isEmpty = tconfirmpassword.getPassword().length == 0;
                confirmPasswordPlaceholder.setVisible(isEmpty);

                // Hide or show the caret based on text presence
                Caret caret = tconfirmpassword.getCaret();
                caret.setVisible(!isEmpty);
            }
        });
    }

    // method actionPerformed()
    // to get the action performed by the user and act accordingly
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == login) {
            String username = tusername.getText();
            char[] password = tpassword.getPassword();
            User authenticatedUser = null;

            // Read UserDetails.txt to check for username and password
            try (BufferedReader reader = Files.newBufferedReader(Paths.get("UserDetails.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] userDetails = line.split(",");
                    // Check if userDetails has enough parts to avoid ArrayIndexOutOfBoundsException
                    if (userDetails.length >= 4 && userDetails[0].equals(username) && userDetails[1].equals(new String(password))) {
                        authenticatedUser = new User(username, userDetails[1]);
                        authenticatedUser.setLoggedOnFirstTime(userDetails[2].equals("1"));
                        authenticatedUser.setDailyCalorieGoal(Integer.parseInt(userDetails[3]));
                        break;
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to read user details.");
            }

            if (authenticatedUser != null) {
                JOptionPane.showMessageDialog(this, "Authentication Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                CurrentUser.getInstance().setUser(authenticatedUser);
                this.dispose(); // Close the current window

                if (authenticatedUser.isLoggedOnFirstTime()) {
                    promptForDailyCalorieGoal(authenticatedUser);
                    // Update the user details in the file
                    try {
                        UIUtilities.updateUserDetails(authenticatedUser);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Failed to update user details.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                final int dailyCalorieGoal = authenticatedUser.getDailyCalorieGoal(); // This variable is effectively final
                SwingUtilities.invokeLater(() -> {
                    GUIWindow guiWindow = null;
                    try {
                        guiWindow = new GUIWindow(dailyCalorieGoal);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    guiWindow.setVisible(true);
                });
            } else {
                JOptionPane.showMessageDialog(this, "Authentication Failed. Invalid username or password.", "Authentication Failed", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == createAcc) {
            c.removeAll(); // Remove all components from the current content pane
            c.repaint(); // Repaint to clear the content pane
            // Now add the components for the "Create Account" menu
            SwingUtilities.invokeLater(() -> {
                setupCreateAccountUI();

                // After setupCreateAccountUI completes, focus components in sequence
                SwingUtilities.invokeLater(() -> {
                    UIUtilities.focusComponentsInSequence();
                });
            });
            c.revalidate(); // Revalidate the content pane after adding new components
        } else if (e.getSource() == createUserAcc) {
            String username = tusername.getText();
            char[] password = tpassword.getPassword();
            char[] confirmPassword = tconfirmpassword.getPassword();

            // Simple validation
            if (username.isEmpty() || password.length == 0 || confirmPassword.length == 0) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.");
            } else if (!Arrays.equals(password, confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match.");
            } else {
                // Create User object
                User newUser = new User(username, new String(password));

                // Write to file
                UIUtilities.writeUserToFile(newUser, "UserDetails.txt");
                // Here
                JOptionPane.showMessageDialog(this, "Account created successfully.");
                try {
                    CalorieTracker.createUserFile(username);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } else if (e.getSource() == backToLogin) {
            // Clear the current UI and set up the login UI again
            c.removeAll();
            setupLoginUI(); // Assuming setupLoginUI() method sets up the login interface
            c.revalidate();
            c.repaint();
        } else if (e.getSource() == showPasswordButton) {
            passwordVisible = !passwordVisible; // Toggle the visibility state first

            // Then, update the icon based on the new visibility state
            if (passwordVisible) {
                tpassword.setEchoChar((char) 0); // Make password visible
                tconfirmpassword.setEchoChar((char) 0);
                showPasswordButton.setIcon(new ImageIcon("show_pass_icon.png")); // Change icon to indicate password is visible
            } else {
                tpassword.setEchoChar('●'); // Hide password
                tconfirmpassword.setEchoChar('●'); // Hide password
                showPasswordButton.setIcon(new ImageIcon("hide_pass_icon.png")); // Change icon to indicate password is hidden
            }
            // No need to repaint the whole panel, only update the button icon
            //showPasswordButton.setIcon(passwordVisible ? new ImageIcon("hide_pass_icon.png") : new ImageIcon("show_pass_icon.png"));
        }
    }
    private void promptForDailyCalorieGoal(User user) {
        while (true) {
            String input = JOptionPane.showInputDialog(this, "Enter your daily calorie goal:", "2000");
            if (input == null || input.trim().isEmpty()) {
                // User cancelled or closed the prompt, handle accordingly
                // For example, you might want to return, show a message, or continue prompting
                JOptionPane.showMessageDialog(this, "You must enter a daily calorie goal to proceed.", "Calorie Goal Required", JOptionPane.WARNING_MESSAGE);
                continue;
            }

            try {
                int dailyGoal = Integer.parseInt(input.trim());
                // Optional: Check for a valid range if required
                if (dailyGoal < 1000 || dailyGoal > 5000) {
                    JOptionPane.showMessageDialog(this, "Please enter a value between 1000 and 5000.", "Invalid Range", JOptionPane.WARNING_MESSAGE);
                    continue;
                }
                user.setDailyCalorieGoal(dailyGoal);
                user.setLoggedOnFirstTime(false);
                break; // Break the loop since we've got a valid input
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    public static void main(String[] args) {
        try {
            // Set cross-platform Java L&F
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            // Handle exception
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new UserPage();
            }
        });
    }
}

// Custom panel class with overridden paintComponent method to paint a background image
class LoginBackgroundPanel extends JPanel {
    private Image originalImage;
    private Image scaledImage;
    private int zoomWidth;
    private int zoomHeight;

    public LoginBackgroundPanel(String imagePath) {
        ImageIcon icon = new ImageIcon(imagePath);
        originalImage = icon.getImage();
        zoomWidth = 500; // The width of the zoomed area you want to draw
        zoomHeight = 500; // The height of the zoomed area you want to draw
    }

    private void scaleImage() {
        // Calculate the region of the image to draw to fill the panel
        int imageWidth = originalImage.getWidth(this);
        int imageHeight = originalImage.getHeight(this);

        // Calculate the top left corner (x,y) of the region to zoom in on the original image
        int x = (imageWidth - zoomWidth) / 2;
        int y = (imageHeight - zoomHeight) / 2;

        // Ensure x and y are not negative
        x = Math.max(x, 0);
        y = Math.max(y, 0);

        // Create a new image containing just the zoomed region
        scaledImage = createImage(new FilteredImageSource(originalImage.getSource(),
                new CropImageFilter(x, y, zoomWidth, zoomHeight)));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Ensure the image is scaled
        if (scaledImage == null) {
            scaleImage();
        }

        // Draw the zoomed-in region
        g.drawImage(scaledImage, 0, 0, getWidth(), getHeight(), this);
    }
}

