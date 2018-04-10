package view;

import control.Controller;
import error.SimulationError;
import event.Event;
import logic.RoadMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.util.List;

public class ToolBar extends JToolBar  implements ObserverTrafficSimulator{

	private JSpinner steps;
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
				try {
					controller.loadEvent(new ByteArrayInputStream(content));
				} catch (SimulationError simulationError) {
					simulationError.printStackTrace();
				}
				mainWindow.setMessage("Events loaded to the simulation!");
			}
		});
		this.add(checkInButton);

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

		// SPINNER
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

		// GENERATE REPORTS
		JButton generateReportsButton = new JButton(new ImageIcon("media/icons/generateReportsButton.png"));
		generateReportsButton.setToolTipText("Generate reports into the reports panel");
		generateReportsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.generateReport();
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
	@Override
	public void simulatorError(int time, RoadMap map, List<Event> event, SimulationError e) {

	}

	@Override
	public void advance(int time, RoadMap map, List<Event> events) {
		this.time.setText(String.valueOf(time + 1));
	}

	@Override
	public void addEvent(int time, RoadMap map, List<Event> events) {
		// empty
	}

	@Override
	public void reset(int time, RoadMap map, List<Event> events) {
		this.steps.setValue(1);
		this.time.setText("0");
	}

	@Override
	public void removeEvent(int time, RoadMap map, List<Event> events) {
		// empty
	}
}
