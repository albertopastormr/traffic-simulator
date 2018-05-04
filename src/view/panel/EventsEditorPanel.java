package view.panel;


import view.MainWindow;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class EventsEditorPanel extends TextAreaPanel {

    private PopUpMenu templatePopUpMenu;

    public EventsEditorPanel(String title, String text, boolean editable, MainWindow mainWindow){
        super(title, editable);
        this.setText(text);

        templatePopUpMenu = new PopUpMenu(mainWindow);
        this.textArea.add(templatePopUpMenu);
        this.textArea.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // empty, its not necessary to be implemented
				if(e.isPopupTrigger() && textArea.isEnabled())
					templatePopUpMenu.show(e.getComponent(), e.getX(), e.getY());
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if(e.isPopupTrigger() && textArea.isEnabled())
                    templatePopUpMenu.show(e.getComponent(), e.getX(), e.getY());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // empty, its not necessary to be implemented
				if(e.isPopupTrigger() && textArea.isEnabled())
					templatePopUpMenu.show(e.getComponent(), e.getX(), e.getY());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // empty, its not necessary to be implemented
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // empty, its not necessary to be implemented
            }
        });
    }

    @Override
    public void setEnabledForExecute(boolean enabled){
        this.textArea.setEnabled(enabled);
    }

}
