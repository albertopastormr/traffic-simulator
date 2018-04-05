package view;

import javax.swing.*;
import java.awt.*;

public abstract class TextAreaPanel extends JPanel {

    protected JTextArea textArea;

    public TextAreaPanel(String title, boolean editable){
        this.setLayout(new GridLayout(1,1));
        this.textArea = new JTextArea(40,30);
        this.textArea.setEditable(editable);
        this.add(new JScrollPane(this.textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS));
        this.setBorder(title);
    }
    public void setBorder(String title){
        this.setBorder(BorderFactory.createTitledBorder(title));
    }
    public JTextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }
    public void clear(){
        this.setTextArea(new JTextArea(40,30));
    }
    public void insert(String value){
        this.textArea.insert(value, this.textArea.getCaretPosition());
    }
}
