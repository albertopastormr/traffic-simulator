package view;

import control.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class MenuBar extends JMenuBar {

	public MenuBar(MainWindow mainWindow, Controller controller){
		super();
		// MANEJO DE FICHEROS
		JMenu filesMenu = new JMenu("Files");
		this.add(filesMenu);
		this.createFilesMenu(filesMenu, mainWindow);
		// SIMULADOR
		JMenu simulatorMenu = new JMenu("Simulator");
		this.add(simulatorMenu);
		this.createSimulatorMenu(simulatorMenu, controller, mainWindow);
		// INFORMES
		JMenu reportsMenu = new JMenu("Reports");
		this.add(reportsMenu);
		this.createReportsMenu(reportsMenu, mainWindow);
	}

	private void createFilesMenu(JMenu menu,MainWindow mainWindow){
		// LOAD
		JMenuItem load = new JMenuItem("Load Events");
		load.setMnemonic(KeyEvent.VK_L);
		load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK)); // Podria fallar InputEvent, cambiado sobre pdf ayuda
		load.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				mainWindow.loadFile();
				mainWindow.showDialog("File has been loaded !");
			}
		});
		// SAVE
		JMenuItem save = new JMenuItem("Save Events");
		save.setMnemonic(KeyEvent.VK_S);
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK)); // Podria fallar InputEvent, cambiado sobre pdf ayuda
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.saveEventsEditor();
				mainWindow.showDialog("File has been saved !");

			}
		});
		// SAVE REPORTS
		JMenuItem saveReports = new JMenuItem("Save Reports");
		saveReports.setMnemonic(KeyEvent.VK_T);
		saveReports.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_MASK)); // Podria fallar InputEvent, cambiado sobre pdf ayuda
		saveReports.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.saveReports();
				mainWindow.showDialog("Actual execution reports have been saved !");
			}
		});
		// EXIT
		JMenuItem exit = new JMenuItem("Exit");
		exit.setMnemonic(KeyEvent.VK_Q);
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK)); // Podria fallar InputEvent, cambiado sobre pdf ayuda
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.exit();
			}
		});

		menu.add(load);
		menu.add(save);
		menu.add(saveReports);
		menu.add(exit);

	}

	private void createSimulatorMenu(JMenu menu, Controller controller, MainWindow mainWindow){
		// EXECUTE
		JMenuItem execute = new JMenuItem("Execute");
		execute.setMnemonic(KeyEvent.VK_E);
		execute.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK)); // Podria fallar InputEvent, cambiado sobre pdf ayuda
		execute.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.execute();
			}
		});
		// RESET
		JMenuItem reset = new JMenuItem("Reset");
		reset.setMnemonic(KeyEvent.VK_R);
		reset.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK)); // Podria fallar InputEvent, cambiado sobre pdf ayuda
		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.resetAll();
			}
		});

		menu.add(execute);
		menu.add(reset);

	}

	private void createReportsMenu(JMenu menu, MainWindow mainWindow){
		// GENERATE
		JMenuItem generate = new JMenuItem("Generate");
		generate.setMnemonic(KeyEvent.VK_G);
		generate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_MASK)); // Podria fallar InputEvent, cambiado sobre pdf ayuda
		generate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.generateReport();
				mainWindow.showDialog("Reports have been generated !");
			}
		});
		// CLEAR
		JMenuItem clear = new JMenuItem("Clear");
		clear.setMnemonic(KeyEvent.VK_G);
		clear.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_MASK)); // Podria fallar InputEvent, cambiado sobre pdf ayuda
		clear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.clearReports();
				mainWindow.showDialog("Reports have been cleared !");
			}
		});

		menu.add(generate);
		menu.add(clear);

	}


}
