import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {

    Image image;
    final int PANEL_WIDTH = 650;
    final int PANEL_HEIGHT = 1000;
    MainPanel() {
        image = new ImageIcon("burger_background.png").getImage();
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(image,0,0,null);
    }
}
