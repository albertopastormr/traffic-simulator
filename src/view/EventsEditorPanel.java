package view;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class EventsEditorPanel extends TextAreaPanel {

    public EventsEditorPanel(String title, String text, boolean editable, MainWindow mainWindow){
        super(title, editable);
        this.setText(text);

        PopupMenu popUp = new PopupMenu(mainWindow);
        this.textArea.add(popUp);
        this.textArea.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if(e.isPopupTrigger() && textArea.isEnabled())
                    popUp.show(e.getComponent(), e.getX(), e.getY());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // PENDIENTE
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }
    public void setText(String text){
        this.textArea.setText(text);
    }
}
