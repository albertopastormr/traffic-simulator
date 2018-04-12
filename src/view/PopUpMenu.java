package view;

import error.SimulationError;
import event.constructor.EventConstructor;
import util.EventParser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PopUpMenu extends JPopupMenu{

	public PopUpMenu(MainWindow mainWindow){
		// OPCIONES EN POPUPMENU
		JMenu templatesItem = new JMenu("New template");
		this.add(templatesItem);
		JMenuItem loadItem = new JMenuItem("Load");
		loadItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.loadFile();
			}
		});
		JMenuItem saveItem = new JMenuItem("Save");
		saveItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.saveEventsEditor();
			}
		});
		this.addSeparator();
		this.add(loadItem);
		this.add(saveItem);

		// OPCIONES EN TEMPLATES
		for(EventConstructor eventConstructor : EventParser.getEvents()){
			JMenuItem menuItem = new JMenuItem(eventConstructor.toString());
			menuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						mainWindow.insertAtEventsEditor(  System.lineSeparator() + "["+ eventConstructor.getTag() +"]" + System.lineSeparator() + eventConstructor.template() + System.lineSeparator());
					} catch (SimulationError simulationError) {
						simulationError.printStackTrace();
					}
				}
			});
			templatesItem.add(menuItem);
		}
	}
}
