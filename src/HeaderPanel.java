import javax.swing.*;
import java.awt.*;

public class HeaderPanel extends JPanel {
    public HeaderPanel() {
        setOpaque(true);
        setBackground(Color.YELLOW);
        setPreferredSize(new Dimension(650, 100));
        setLayout(null);

        JLabel welcomeLabel = new JLabel("Welcome back (name)", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setBounds(0, 0, 650, 100);
        add(welcomeLabel);
    }
}

