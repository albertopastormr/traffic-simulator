package view;

import logic.SimulationObject;

import javax.swing.*;
import java.util.List;

public class ListModel<T extends SimulationObject> extends DefaultListModel<T> {

	private List<T> list;

	ListModel() { this.list = null;}

	public void setList(List<T> list){
		this.list = list;
		fireContentsChanged(this, 0, this.list.size());
	}

	@Override
	public int getSize() {
		return (this.list == null ? 0 : this.list.size());
	}

	@Override
	public T getElementAt(int index) {
		return this.list.get(index);
	}
}
