import javax.swing.*;

public class BackgroundImagePanel extends JPanel {
    private JLabel label;
    public BackgroundImagePanel() {
        label = new JLabel();
        add(label);
    }

    public void setImage(Object obj) {
        label.setIcon(new ImageIcon("images/background_images/"+obj));
    }
}
