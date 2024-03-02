import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class GUIWindow extends JFrame {
    static boolean addCaloriesWindowOpen = false; // Flag to keep track of window being open
    static boolean settingsWindowOpen = false;
    private static CalorieWheelPanel cwp;

    public GUIWindow(int dailyCalorieGoal) throws IOException {
        setTitle("Calorie Tracker");
        setSize(650, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Set up currentCalories
        User currentUser = CurrentUser.getInstance().getUser();
        if (currentUser == null) {
            throw new IllegalStateException("Current user is not set.");
        }
        String username = currentUser.getUsername();
        int currentCalories = CalorieTracker.getLatestCalorieValues(username);
        System.out.println(currentCalories);

        // Create the CalorieWheelPanel with the dailyCalorieGoal
        cwp = new CalorieWheelPanel(dailyCalorieGoal, currentCalories); // Set maxCalories to dailyCalorieGoal
        cwp.setOpaque(false);
        cwp.setBounds(125, 150, 400, 400);

        JLayeredPane layeredPane = createLayeredPane();
        add(layeredPane);

        setVisible(true);
    }
    // Method to update the calorie wheel with the user's daily goal
    public static void updateCalorieWheel(int maxCalories) {
        cwp.setMaxCalories(maxCalories);
        cwp.repaint();
    }

    private JLayeredPane createLayeredPane() {
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 650, 1000);

        // Main Panel
        MainPanel mp = new MainPanel();
        mp.setOpaque(true);
        mp.setBackground(Color.MAGENTA);
        mp.setBounds(0, 0, 650, 1000);

        // Bottom Panel
        JPanel bp = createBottomPanel(cwp);

        // Layering the components
        layeredPane.add(mp, Integer.valueOf(0));
        layeredPane.add(cwp, Integer.valueOf(1)); // Use the class member cwp
        layeredPane.add(bp, Integer.valueOf(2));

        return layeredPane;
    }

    private JPanel createBottomPanel(CalorieWheelPanel cwp) {
        JPanel bp = new JPanel();
        bp.setOpaque(true);
        bp.setBackground(Color.ORANGE);
        bp.setBounds(0, 890, 650, 80);
        bp.setLayout(null);

        // Buttons
        JButton addButton = createIconButton("add_button.png", 300, 5);
        JButton graphButton = createIconButton("graph_button.png", 210, 5);
        JButton settingsButton = createIconButton("settings_button.png", 390, 5);

        // Add button action
        addButton.addActionListener(e -> {
            if (!addCaloriesWindowOpen) {
                addCaloriesWindowOpen = true;
                AddCaloriesWindow acw = new AddCaloriesWindow(cwp);
                acw.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                        addCaloriesWindowOpen = false; // Reset the flag when the window is closed
                    }
                });
                acw.setVisible(true);
                System.out.println("Add Calories Window opened.");
            }
        });

        // Add buttons to bottom panel
        bp.add(addButton);
        bp.add(graphButton);
        bp.add(settingsButton);

        return bp;
    }

    private JButton createIconButton(String iconPath, int x, int y) {
        ImageIcon icon = new ImageIcon(iconPath);
        JButton button = new JButton();
        button.setIcon(icon);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setBounds(x, y, 64, 64);
        return button;
    }

/*    public static void main(String[] args) { // Put main into UserPage instead
        SwingUtilities.invokeLater(() -> new GUIWindow());
    }*/
}
