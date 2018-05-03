package view.menubar;

import control.Controller;
import error.SimulationError;
import view.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.ByteArrayInputStream;

public class MenuBar extends JMenuBar {

	public MenuBar(MainWindow mainWindow, Controller controller){
		super();
		// MANEJO DE FICHEROS
		JMenu filesMenu = new JMenu("Files");
		this.createFilesMenu(filesMenu, mainWindow);
		this.add(filesMenu);
		// SIMULADOR
		JMenu simulatorMenu = new JMenu("Simulator");
		this.createSimulatorMenu(simulatorMenu, controller, mainWindow);
		this.add(simulatorMenu);
		// INFORMES
		JMenu reportsMenu = new JMenu("Reports");
		this.createReportsMenu(reportsMenu, mainWindow);
		this.add(reportsMenu);
	}

	private void createFilesMenu(JMenu menu,MainWindow mainWindow){
		// LOAD EVENTS
		JMenuItem load = new JMenuItem("Load Events");
		load.setMnemonic(KeyEvent.VK_L);
		load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK));
		load.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(mainWindow.loadFile())
					mainWindow.showDialog("File has been loaded !");
			}
		});
		// SAVE EVENTS
		JMenuItem save = new JMenuItem("Save Events");
		save.setMnemonic(KeyEvent.VK_S);
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(mainWindow.saveEventsEditor())
					mainWindow.showDialog("File has been saved !");

			}
		});
		// SAVE REPORTS
		JMenuItem saveReports = new JMenuItem("Save Reports");
		saveReports.setMnemonic(KeyEvent.VK_T);
		saveReports.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_MASK));
		saveReports.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(mainWindow.saveReports())
					mainWindow.showDialog("Actual execution reports have been saved !");
			}
		});
		// EXIT
		JMenuItem exit = new JMenuItem("Exit");
		exit.setMnemonic(KeyEvent.VK_Q);
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.exit();
			}
		});

		menu.add(load);
		menu.add(save);
		menu.add(saveReports);
		menu.addSeparator();
		menu.add(exit);
	}

	private void createSimulatorMenu(JMenu menu, Controller controller, MainWindow mainWindow){
		// EXECUTE
		JMenuItem execute = new JMenuItem("Execute");
		execute.setMnemonic(KeyEvent.VK_E);
		execute.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
		execute.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.execute();
			}
		});
		// RESET
		JMenuItem reset = new JMenuItem("Reset");
		reset.setMnemonic(KeyEvent.VK_R);
		reset.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.resetAll();
			}
		});
		// OUTPUT OPTION
		JCheckBoxMenuItem outputOption = new JCheckBoxMenuItem("Console Output");
		outputOption.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AbstractButton abstractButton = (AbstractButton) e.getSource();
				boolean selected = abstractButton.getModel().isSelected();
				try {
					mainWindow.switchOutputStream((selected ? MainWindow.OutputOption.CONSOLE : MainWindow.OutputOption.GRAPHIC));
				} catch (SimulationError simulationError) {
					simulationError.printStackTrace();
				}
			}
		});

		// CHECK-IN
		JMenuItem checkIn = new JMenuItem("Check-In");
		checkIn.setMnemonic(KeyEvent.VK_I);
		checkIn.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK));
		checkIn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.reset();
				byte[] content = mainWindow.getEventsEditorText().getBytes();
				if(content.length != 0){
					try {
						controller.loadEvent(new ByteArrayInputStream(content));
						mainWindow.showDialog("Events loaded to the simulation!");
					} catch (Exception err) {
						controller.reset();
						mainWindow.showErrorDialog("ERROR: error loading events from the Editor Panel:\n" + err.getMessage());
					}
				}
				else
					mainWindow.showDialog("Events editor is empty ! Can't load any events !");
			}
		});
		// CLEAR EVENTS AREA
		JMenuItem clearEvents = new JMenuItem("Clear Events Editor");
		clearEvents.setMnemonic(KeyEvent.VK_C);
		clearEvents.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
		clearEvents.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.clearEventsEditor();
				mainWindow.showDialog("Events Editor has been cleared !");
			}
		});

		menu.add(execute);
		menu.add(reset);
		menu.add(outputOption);
		menu.addSeparator();
		menu.add(checkIn);
		menu.add(clearEvents);

	}

	private void createReportsMenu(JMenu menu, MainWindow mainWindow){
		// GENERATE ALL REPORTS
		JMenuItem generateAll = new JMenuItem("Generate (all)");
		generateAll.setMnemonic(KeyEvent.VK_G);
		generateAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_MASK));
		generateAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.generateReport();
				mainWindow.showDialog("Reports have been generated !");
			}
		});
		// GENERATE SELECTED REPORTS
		JMenuItem generateSelected = new JMenuItem("Generate (select)");
		generateSelected.setMnemonic(KeyEvent.VK_Z);
		generateSelected.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
		generateSelected.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.setVisibleReportsDialog(true);
				mainWindow.showDialog("Reports have been generated !");
			}
		});
		// CLEAR
		JMenuItem clear = new JMenuItem("Clear");
		clear.setMnemonic(KeyEvent.VK_G);
		clear.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.ALT_MASK));
		clear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.clearReports();
				mainWindow.showDialog("Reports have been cleared !");
			}
		});

		menu.add(generateAll);
		menu.add(generateSelected);
		menu.add(clear);

	}

	public void setEnabledForExecute(boolean enabled){
		for(Component comp : this.getComponents())
			comp.setEnabled(enabled);
	}
}
