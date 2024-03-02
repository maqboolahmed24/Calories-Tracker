import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FooterPanel extends JPanel {
    private final MainGUI mainGUI; //reference to main so it can use SwitchPanel method here

    public FooterPanel(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
        setOpaque(true);
        setBackground(Color.YELLOW);
        // You've set the preferred size twice, which is redundant. Remove one.
        setPreferredSize(new Dimension(650, 80));
        setLayout(null); // You're using a null layout, so you need to set bounds for each component

        JButton homeButton = createButton("home_button.png");
        JButton reportsButton = createButton("graph_button.png");
        JButton settingsButton = createButton("settings_button.png");

        // placing each button
        homeButton.setBounds(100, 10, homeButton.getIcon().getIconWidth(), homeButton.getIcon().getIconHeight());
        reportsButton.setBounds(250, 10, reportsButton.getIcon().getIconWidth(), reportsButton.getIcon().getIconHeight());
        settingsButton.setBounds(400, 10, settingsButton.getIcon().getIconWidth(), settingsButton.getIcon().getIconHeight());

        // Buttons Event listeners
        homeButton.addActionListener(e -> mainGUI.switchPanel("Home"));
        reportsButton.addActionListener(e -> mainGUI.switchPanel("Reports"));
        settingsButton.addActionListener(e -> mainGUI.switchPanel("Settings"));

        // adding buttons to the panel
        add(homeButton);
        add(reportsButton);
        add(settingsButton);
    }

    private JButton createButton(String iconPath) {
        ImageIcon icon = new ImageIcon(iconPath);
        JButton button = new JButton();
        button.setIcon(icon);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        return button;
    }
}
