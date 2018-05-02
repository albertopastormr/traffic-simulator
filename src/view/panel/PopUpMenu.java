package view.panel;

import error.SimulationError;
import event.constructor.EventConstructor;
import util.EventParser;
import view.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PopUpMenu extends JPopupMenu{

	public PopUpMenu(MainWindow mainWindow){
		// OPCIONES EN POPUPMENU //
		JMenu templatesItem = new JMenu("New template");
		this.add(templatesItem);
		// LOAD OPTION
		JMenuItem loadItem = new JMenuItem("Load");
		loadItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.loadFile();
			}
		});
		// SAVE OPTION
		JMenuItem saveItem = new JMenuItem("Save");
		saveItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.saveEventsEditor();
			}
		});
		// CLEAR OPTION
		JMenuItem clearItem = new JMenuItem("Clear");
		clearItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.clearEventsEditor();
			}
		});
		this.addSeparator();
		this.add(loadItem);
		this.add(saveItem);
		this.add(clearItem);


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
