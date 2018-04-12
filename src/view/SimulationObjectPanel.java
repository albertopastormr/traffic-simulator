package view;

import logic.SimulationObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class SimulationObjectPanel<T extends SimulationObject> extends JPanel {

	private ListModel<T> listModel;
	private JList<T> objList;

	public SimulationObjectPanel(String title){
		this.listModel = new ListModel<>();
		this.objList = new JList<>(this.listModel);
		this.addCleanSelectionListener(objList);
		this.add(new JScrollPane(objList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);
	}

	private void addCleanSelectionListener(JList<?> list){
		list.addKeyListener((new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar() == ReportsDialog.CLEAN_KEY)
					list.clearSelection();
			}

			@Override
			public void keyPressed(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}
		}));
	}
	public List<T> getSelectedItems(){
		List<T> l = new ArrayList<>();
		for(int i : this.objList.getSelectedIndices())
			l.add(this.listModel.getElementAt(i));
		return l;
	}
	public void setListModel(List<T> list){
		this.listModel.setList(list);
	}
}
