import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.Document;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class AddCaloriesWindow extends JFrame {
    private CalorieWheelPanel calorieWheelPanel;
    private HashMap<String, Integer> foodCaloriesMap = new HashMap<>();
    private JTextField enterFoodNameField; // Declare as class-level field
    private JTextField enterGramsOfFood; // Declare as class-level field
    private JButton submitFoodAndGramsButton; // Declare as class-level field

    public AddCaloriesWindow(CalorieWheelPanel cwp) {
        this.calorieWheelPanel = cwp;
        setTitle("Add Calories Consumed");
        setSize(300, 300);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        loadCaloriesData();

        // Component set-up
        JLabel enterCals = new JLabel("Enter Calories:");
        JTextField enterCalsTextField = new JTextField();
        JButton enterCalsSubmitButton = new JButton("Submit");

        JLabel selectFoodLabel = new JLabel("Select Food:");
        enterFoodNameField = new JTextField();
        JLabel enterAmountLabel = new JLabel("Enter Amount:");
        enterGramsOfFood = new JTextField();
        submitFoodAndGramsButton = new JButton("Submit");
        JLabel inGramsLabel = new JLabel("(in grams)");

        enterCalsSubmitButton.setEnabled(false); // Button disabled initially to prevent empty input

        // Add document listener to text field
        enterCalsTextField.getDocument().addDocumentListener(new DocumentListener() {
            // Check all instances where text field could be empty
            public void changedUpdate(DocumentEvent e) {
                check();
            }
            public void removeUpdate(DocumentEvent e) {
                check();
            }
            public void insertUpdate(DocumentEvent e) {
                check();
            }

            // Update button based on whether empty or not
            public void check() {
                if (enterCalsTextField.getText().trim().isEmpty()) {
                    enterCalsSubmitButton.setEnabled(false);
                } else {
                    enterCalsSubmitButton.setEnabled(true);
                }
            }
        });

        // Enter cals text field input filters
        Document doc = enterCalsTextField.getDocument();
        if (doc instanceof AbstractDocument) {
            ((AbstractDocument) doc).setDocumentFilter(new IntegerFilter());
        }

        // Submit Button action listener
        enterCalsSubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int newCalories = Integer.parseInt(enterCalsTextField.getText());
                AddCaloriesWindow.this.calorieWheelPanel.setCurrentCalories(newCalories);
                calorieWheelPanel.repaint(); // Update display of calorie wheel
                User currentUser = CurrentUser.getInstance().getUser();
                String username = currentUser.getUsername();
                //System.out.println(username); debug
                try {
                    CalorieTracker.addOrUpdateCalories(username,newCalories); // Need current user here.
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // Food Search Bar action listener
        submitFoodAndGramsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String foodName = enterFoodNameField.getText().toLowerCase().trim();
                if (!foodCaloriesMap.containsKey(foodName)) {
                    JOptionPane.showMessageDialog(AddCaloriesWindow.this, "Food item not found. Please enter a valid food name.");
                    return; // Exit the method if food name is not valid
                }
                int foodCaloriesPer100g = foodCaloriesMap.getOrDefault(foodName, 0);
                int amount;

                try {
                    amount = Integer.parseInt(enterGramsOfFood.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(AddCaloriesWindow.this, "Please enter a valid number for the amount.");
                    return;
                }

                double caloriesPerGram = foodCaloriesPer100g / 100.0;
                int additionalCalories = (int) Math.round(caloriesPerGram * amount);

                // Retrieve the current total calories
                int currentCalories = AddCaloriesWindow.this.calorieWheelPanel.getCurrentCalories();

                // Calculate the new total calories
                int newTotalCalories = currentCalories + additionalCalories;

                // Set the new total calories
                AddCaloriesWindow.this.calorieWheelPanel.setCurrentCalories(newTotalCalories);
                calorieWheelPanel.repaint(); // Update display of calorie wheel

                // Retrieve the current user's username
                User currentUser = CurrentUser.getInstance().getUser();
                if (currentUser == null || currentUser.getUsername() == null || currentUser.getUsername().isEmpty()) {
                    JOptionPane.showMessageDialog(AddCaloriesWindow.this, "Error: User not found. Please log in again.");
                    return; // Exit the method if user information is not available
                }
                String username = currentUser.getUsername();

                // Update the user's calorie intake for the day
                try {
                    CalorieTracker.addOrUpdateCalories(username, additionalCalories);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(AddCaloriesWindow.this, "Failed to update calorie intake. Please try again.");
                }
            }
        });

        // Component positioning
        enterCals.setBounds(10, 10, 100, 30);
        enterCalsTextField.setBounds(110, 10, 100, 20);
        enterCalsSubmitButton.setBounds(110,30,100,20);

        selectFoodLabel.setBounds(10, 60, 100, 30);
        enterFoodNameField.setBounds(110, 65, 100, 20);
        enterGramsOfFood.setBounds(110, 85, 100, 20);
        enterAmountLabel.setBounds(10, 82, 100, 30);
        submitFoodAndGramsButton.setBounds(110, 105, 100, 20);
        inGramsLabel.setBounds(15, 100, 100, 20);

        // Adding to frame
        add(enterCals);
        add(enterCalsTextField);
        add(enterCalsSubmitButton);

        add(selectFoodLabel);
        add(enterFoodNameField);
        add(enterGramsOfFood);
        add(enterAmountLabel);
        add(submitFoodAndGramsButton);
        add(inGramsLabel);

    }

    private void loadCaloriesData() {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader("calories.csv"))) {
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // Skip the first line (header)
                    continue;
                }
                String[] values = line.split(",");
                if(values.length >= 4) {
                    String foodName = values[1].trim().toLowerCase();
                    try {
                        String calorieString = values[3].trim().replaceAll("[^\\d]", "");
                        int calories = Integer.parseInt(calorieString);
                        System.out.println("Food: " + foodName + ", Calories: " + calories); // Debugging line
                        foodCaloriesMap.put(foodName, calories);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private int getCaloriesForFood(String foodName) {
        return foodCaloriesMap.getOrDefault(foodName.toLowerCase(), 0);
    }
}
