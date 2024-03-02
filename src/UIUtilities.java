import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

public class UIUtilities {

    public static void clearFocusListeners(JTextField... textFields) {
        for (JTextField textField : textFields) {
            FocusListener[] listeners = textField.getFocusListeners();
            for (FocusListener l : listeners) {
                textField.removeFocusListener(l);
            }
        }
    }

    public static void focusComponentsInSequence(JTextField... textFields) {
        // Assuming the usage of SwingUtilities.invokeLater for thread safety
        SwingUtilities.invokeLater(() -> {
            try {
                Robot robot = new Robot();
                for (JTextField textField : textFields) {
                    textField.requestFocusInWindow();
                    robot.keyPress(KeyEvent.VK_TAB);
                    robot.keyRelease(KeyEvent.VK_TAB);
                    // Adding a small delay so that focus traversal can occur
                    Thread.sleep(100);
                }
                // Optionally, defocus all components at the end
                // This might require passing a container reference to remove focus from all children
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void writeUserToFile(User user, String filePath) {
        try {
            Path path = Paths.get(filePath);
            BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            // Assuming that the default for new users is loggedOnFirstTime = true and dailyCalorieGoal = 0
            String userInfo = String.join(",",
                    user.getUsername(),
                    user.getPassword(),
                    "1", // true for loggedOnFirstTime, as it's a new user
                    "0"  // default calorie goal
            );
            writer.write(userInfo);
            writer.newLine();
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            // Handling the error properly depends on the context, might need to pass a reference to show a dialog
        }
    }
    public static void saveUserDetails(User updatedUser) throws IOException {
        File file = new File("UserDetails.txt");
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userDetails = line.split(",");
                if (userDetails[0].equals(updatedUser.getUsername())) {
                    // Replace with updated user details
                    line = String.join(",",
                            updatedUser.getUsername(),
                            updatedUser.getPassword(),
                            updatedUser.isLoggedOnFirstTime() ? "1" : "0",
                            String.valueOf(updatedUser.getDailyCalorieGoal())
                    );
                }
                lines.add(line);
            }
        }

        // Write all user details back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String userDetail : lines) {
                writer.write(userDetail);
                writer.newLine();
            }
        }
    }

    public static void updateUserDetails(User user) throws IOException {
        // Read all lines from the file
        List<String> lines = Files.readAllLines(Paths.get("UserDetails.txt"));
        for (int i = 0; i < lines.size(); i++) {
            String[] userDetails = lines.get(i).split(",");
            if (userDetails[0].equals(user.getUsername())) {
                // Update the line with new details
                userDetails[2] = "0"; // Set loggedOnFirstTime to false
                userDetails[3] = String.valueOf(user.getDailyCalorieGoal()); // Update dailyCalorieGoal
                lines.set(i, String.join(",", userDetails));
                break;
            }
        }

        // Write all user details back to the file
        Files.write(Paths.get("UserDetails.txt"), lines);
    }
}