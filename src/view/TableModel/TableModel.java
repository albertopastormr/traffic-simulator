package view.TableModel;

import control.Controller;
import view.observer.ObserverTrafficSimulator;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
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

    public void clear(){
        this.list = new ArrayList<>();
        this.columnIds = null;
    }

	public void setColumnIds(String[] columnIds) {
		this.columnIds = columnIds;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getSize(){
    	return this.list.size();
	}

	public boolean isEmpty(){
		return this.list.isEmpty();
	}
}
