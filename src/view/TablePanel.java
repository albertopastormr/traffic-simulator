package view;

import javax.swing.*;
import java.awt.*;

public class TablePanel<T> extends JPanel {

    private TableModel<T> model;

    public TablePanel(String borderId, TableModel<T> model){
        this.setLayout(new GridLayout(1,1));
        this.setBorder(BorderFactory.);
        this.model = model;
        JTable table = new JTable(this.model);
        this.add(new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
    }
}
