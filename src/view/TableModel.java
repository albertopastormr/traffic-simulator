package view;

import control.Controller;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public abstract class TableModel<T> extends DefaultTableModel implements ObserverTrafficSimulator {

    protected String[] columnIds;
    protected List<T> list;

    public TableModel(String[] columnIdEvents, Controller controller){
        this.list = null;
        this.columnIds = columnIdEvents;
        controller.addObserver(this);
    }

    @Override
    public int getRowCount() {
        return (this.list == null ? 0 : this.list.size() );
    }

    @Override
    public int getColumnCount() {
        return this.columnIds.length;
    }

    @Override
    public String getColumnName(int column) {
        return this.columnIds[column];
    }
}
