package view.toolbar;

import control.Controller;
import error.SimulationError;
import event.Event;
import logic.RoadMap;
import view.MainWindow;
import view.observer.ObserverTrafficSimulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.util.List;

public class ToolBar extends JToolBar  implements ObserverTrafficSimulator {

	private JSpinner steps;
	private JSpinner delay;
	private JTextField time;

	public ToolBar(MainWindow mainWindow, Controller controller){
		super();
		controller.addObserver(this);

		// LOAD EVENTS
		JButton loadButton = new JButton(new ImageIcon("media/icons/loadButton.png"));
		loadButton.setToolTipText("Load events file");
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.loadFile();
			}
		});
		this.add(loadButton);

		// CHECK-IN
		JButton checkInButton = new JButton(new ImageIcon("media/icons/checkInButton.png"));
		checkInButton.setToolTipText("Execute pre-loaded events");
		checkInButton.addActionListener(new ActionListener() {
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
		this.add(checkInButton);

		this.addSeparator();

		// SAVE EVENTS EDITOR
		JButton saveEventsEditorButton = new JButton(new ImageIcon("media/icons/saveEventsEditorButton.png"));
		saveEventsEditorButton.setToolTipText("Save events editor text into a file");
		saveEventsEditorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.saveEventsEditor();
			}
		});
		this.add(saveEventsEditorButton);

		// CLEAR EVENTS EDITOR
		JButton clearEventsEditorButton = new JButton(new ImageIcon("media/icons/clearEventsEditorButton.png"));
		clearEventsEditorButton.setToolTipText("Clear events editor");
		clearEventsEditorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.clearEventsEditor();
			}
		});
		this.add(clearEventsEditorButton);


		this.addSeparator();


		// EXECUTE
		JButton executeButton = new JButton(new ImageIcon("media/icons/executeButton.png"));
		executeButton.setToolTipText("Execute the pre-loaded simulation a number of selected steps");
		executeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.execute();
			}
		});
		this.add(executeButton);

		// STOP
		JButton stopButton = new JButton(new ImageIcon("media/icons/stopButton.png"));
		stopButton.setToolTipText("Stop the current simulation");
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.stopExecution();
			}
		});
		this.add(stopButton);

		// RESET
		JButton resetButton = new JButton(new ImageIcon("media/icons/resetButton.png"));
		resetButton.setToolTipText("Reset the current execution");
		resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.resetAll();
			}
		});
		this.add(resetButton);

		// DELAY SPINNER
		this.add( new JLabel(" Delay: "));
		this.delay = new JSpinner(new SpinnerNumberModel(5,0,15000,1));
		this.delay.setToolTipText("delay the current execution a number of ms");
		this.delay.setMaximumSize(new Dimension(40, 50));
		this.delay.setMinimumSize(new Dimension(40,50));
		this.delay.setValue(0);
		this.add(delay);

		// STEPS SPINNER
		this.add( new JLabel(" Steps: "));
		this.steps = new JSpinner(new SpinnerNumberModel(5,1,1000,1));
		this.steps.setToolTipText("steps to execute: 1-1000");
		this.steps.setMaximumSize(new Dimension(40, 50));
		this.steps.setMinimumSize(new Dimension(40,50));
		this.steps.setValue(1);
		this.add(steps);

		// TIME
		this.add(new JLabel(" Time: "));
		this.time = new JTextField("0", 5);
		this.time.setToolTipText("Actual Time");
		this.time.setMaximumSize(new Dimension(70, 70));
		this.time.setMinimumSize(new Dimension(70, 70));
		this.time.setEditable(false);
		this.add(this.time);

		// GENERATE REPORTS AREA
		JButton generateReportsButton = new JButton(new ImageIcon("media/icons/generateReportsButton.png"));
		generateReportsButton.setToolTipText("Generate reports into the reports panel");
		generateReportsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.setVisibleReportsDialog(true);
			}
		});
		this.add(generateReportsButton);

		// CLEAR REPORTS AREA
		JButton clearReportsButton = new JButton(new ImageIcon("media/icons/clearReportsButton.png"));
		clearReportsButton.setToolTipText("Clear reports panel");
		clearReportsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.clearReports();
			}
		});
		this.add(clearReportsButton);

		this.addSeparator();

		// SAVE REPORTS FILE
		JButton saveReportsButton = new JButton(new ImageIcon("media/icons/saveReportsButton.png"));
		saveReportsButton.setToolTipText("Save actual reports to a file");
		saveReportsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.saveReports();
			}
		});
		this.add(saveReportsButton);

		this.addSeparator();

		// EXIT
		JButton exitButton = new JButton(new ImageIcon("media/icons/exitButton.png"));
		exitButton.setToolTipText("Exit from traffic Simulator");
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.exit();
			}
		});
		this.add(exitButton);
	}

	public int getSteps(){
		return (int) this.steps.getValue();
	}
	public int getDelay(){ return (int) this.delay.getValue();}
	public Integer getTime() {
		return Integer.parseInt(time.getText());
	}

	@Override
	public void simulatorError(int time, RoadMap map, List<Event> event, SimulationError e) {
		// empty, its not necessary to be implemented
	}

	@Override
	public void advance(int time, RoadMap map, List<Event> events) {
		this.time.setText(String.valueOf(time + 1));
	}

	@Override
	public void addEvent(int time, RoadMap map, List<Event> events) {
		// empty, its not necessary to be implemented
	}

	@Override
	public void reset(int time, RoadMap map, List<Event> events) {
		this.steps.setValue(1);
		this.delay.setValue(0);
		this.time.setText("0");
	}

	@Override
	public void removeEvent(int time, RoadMap map, List<Event> events) {
		// empty, its not necessary to be implemented
	}
}
