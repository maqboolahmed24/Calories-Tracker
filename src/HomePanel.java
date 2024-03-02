import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel {

    public HomePanel() {
        setLayout(new BorderLayout());

        // Example: Add a welcome label to the top of the panel
        JLabel welcomeLabel = new JLabel("HomePage", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 70));
        add(welcomeLabel, BorderLayout.NORTH);

        // Set the preferred size of the panel if necessary
        setPreferredSize(new Dimension(650, 1000));
    }
}
