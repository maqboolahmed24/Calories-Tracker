import javax.swing.*;

public class BackgroundImageFrame extends JFrame {
    private ImageList imageList;
    private BackgroundImagePanel imagePane;

    public BackgroundImageFrame(ImageList imageList, BackgroundImagePanel imagePane) {
        this.imageList = imageList;
        this.imagePane = imagePane;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, imageList, imagePane);
        getContentPane().add(splitPane);
        setSize(600, 500);
        setVisible(true);
    }

}
