import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.io.File;

public class ImageList extends JList<Object> {
    private DefaultListModel listModel;
    public ImageList() {
       listModel = new DefaultListModel();

       File[] pictures = new File("images/background_images").listFiles();

       for (File pic: pictures) {
           listModel.addElement(pic.getName());
       }

       setModel(listModel);
    }

}

class Listener implements ListSelectionListener {
    private ListModel listModel;
    private BackgroundImagePanel imagePanel;

    public Listener(ListModel listModel, BackgroundImagePanel imagePanel) {
        this.listModel = listModel;
        this.imagePanel = imagePanel;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        ListSelectionModel lsm = (ListSelectionModel) e.getSource();

        if(!lsm.isSelectionEmpty()) {
            int minIndex = lsm.getMinSelectionIndex();
            int maxIndex = lsm.getMaxSelectionIndex();

            for (int i = minIndex; i <= maxIndex; i ++) {
                if(lsm.isSelectedIndex(i)) {
                    imagePanel.setImage(listModel.getElementAt(i));
                }
            }
        }
    }
}
