import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodSearchBar extends JPanel {
    private JTextField searchField;
    private JComboBox<String> comboBox;
    private Map<String, Integer> foodCaloriesMap;

    public FoodSearchBar() {
        initializeFoodCaloriesMap();
        searchField = new JTextField(20);
        comboBox = new JComboBox<>();
        comboBox.setEditable(false);
        comboBox.setModel(new DefaultComboBoxModel<>());

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Add KeyListener to JTextField
        searchField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                String searchText = searchField.getText().toLowerCase();
                List<String> searchResults = search(searchText);

                // Update combo box with the search results
                comboBox.setModel(new DefaultComboBoxModel<>(searchResults.toArray(new String[0])));
                comboBox.setPopupVisible(searchResults.size() > 0);
            }
        });

        add(searchField);
        add(comboBox);
    }

    private void initializeFoodCaloriesMap() {
        foodCaloriesMap = new HashMap<>();
        foodCaloriesMap.put("pasta", 350);
        foodCaloriesMap.put("rice", 130);
        foodCaloriesMap.put("mince beef", 250);
        foodCaloriesMap.put("chicken breast", 165);
        foodCaloriesMap.put("crisps", 160);
        foodCaloriesMap.put("lamb kebab", 480);
        foodCaloriesMap.put("bread", 95);
    }

    private List<String> search(String searchText) {
        List<String> results = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : foodCaloriesMap.entrySet()) {
            if (entry.getKey().toLowerCase().contains(searchText)) {
                results.add(entry.getKey() + " - " + entry.getValue() + " cal");
            }
        }
        return results;
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public JComboBox<String> getComboBox() {
        return comboBox;
    }
}