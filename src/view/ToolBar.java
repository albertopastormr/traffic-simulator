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
		checkInButton.setToolTipText("Load events to the simulation");
		checkInButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.reset();
				byte[] content = mainWindow.getEventsEditorText().getBytes;
				try {
					controller.loadEvent(new ByteArrayInputStream(content));
				} catch (SimulationError simulationError) {
					simulationError.printStackTrace();
				}
				mainWindow.setMessage("Events loaded to the simulation!");
			}
		});
		this.add(checkInButton);

		// SPINNER
		this.add( new JLabel(" Pasos: "));
		this.steps = new JSpinner(new SpinnerNumberModel(5,1,1000,1));
		this.steps.setToolTipText("steps to execute: 1-1000");
		this.steps.setMaximumSize(new Dimension(70, 70));
		this.steps.setMinimumSize(new Dimension(70,70));
		this.steps.setValue(1);
		this.add(steps);

		// TIME
		this.add(new JLabel(" Tiempo: "));
		this.time = new JTextField("0", 5);
		this.time.setToolTipText("Actual Time");
		this.time.setMaximumSize(new Dimension(70, 70));
		this.time.setMinimumSize(new Dimension(70, 70));
		this.time.setEditable(false);
		this.add(this.time);

		// REPORTS
		JButton generateReportsButton = new JButton(new ImageIcon("media/icons/generateReportsButton.png"));
		generateReportsButton.setToolTipText("Generate reports");
		generateReportsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.generateReports();
			}
		});
		this.add(generateReportsButton);


	}

	@Override
	public void simulatorError(int time, RoadMap map, List<Event> event, SimulationError e) {

	}

	@Override
	public void advance(int time, RoadMap map, List<Event> events) {
		this.time.setText(""+this.time);
	}

	@Override
	public void addEvent(int time, RoadMap map, List<Event> events) {

	}

	@Override
	public void reset(int time, RoadMap map, List<Event> events) {
		this.steps.setValue(1);
		this.time.setText("0");
	}
}
