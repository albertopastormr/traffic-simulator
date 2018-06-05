package view.panel;

import logic.SimulationObject;
import view.dialog.ReportsDialog;

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
		this.objList.setMinimumSize(new Dimension(190, 500));
		this.objList.setPreferredSize(new Dimension(190, 500));
		this.add(new JScrollPane(objList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);
		setBorder(BorderFactory.createTitledBorder(title));
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
				if(e.getKeyChar() == ReportsDialog.CLEAN_KEY)
					list.clearSelection();
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyChar() == ReportsDialog.CLEAN_KEY)
					list.clearSelection();
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
