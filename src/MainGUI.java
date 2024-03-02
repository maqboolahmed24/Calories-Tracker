import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI extends JFrame {
    private CardLayout cardLayout = new CardLayout(); // Declare CardLayout here
    private JPanel cardsPanel; // This will be the BackgroundPanel

    public MainGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(650, 1000));

        // initialise and add the header and footer panels to all panels
        add(new HeaderPanel(), BorderLayout.NORTH);
        add(new FooterPanel(this), BorderLayout.SOUTH);

        // Create the BackgroundPanel with CardLayout
        cardsPanel = new BackgroundPanel("burger_background.png");
        cardsPanel.setLayout(cardLayout); // Set the CardLayout for the BackgroundPanel
        cardsPanel.add(new HomePanel(), "Home");
        //cardsPanel.add(new ReportPanel(), "Reports");
        cardsPanel.add(new SettingsPanel(), "Settings");

        // Adds the BackgroundPanel to the centre
        add(cardsPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Method to switch between cards
    public void switchPanel(String cardName) {
        System.out.println("switching pages");
        cardLayout.show(cardsPanel, cardName);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGUI());
    }
}
