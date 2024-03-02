import javax.swing.*;
import java.awt.*;

public class SettingsPanel extends JPanel {

    public SettingsPanel() {
        setLayout(new BorderLayout());

        // Example: Add a welcome label to the top of the panel
        JLabel welcomeLabel = new JLabel("Settings", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 70));
        add(welcomeLabel, BorderLayout.NORTH);

        // Set the preferred size of the panel if necessary
        setPreferredSize(new Dimension(650, 1000));
    }
}

