package view.panel;

import control.Controller;
import error.SimulationError;
import event.Event;
import logic.RoadMap;
import view.observer.ObserverTrafficSimulator;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StatusBarPanel extends JPanel implements ObserverTrafficSimulator {

	private JLabel executeInfo;

	public StatusBarPanel(String message, Controller controller){
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.executeInfo = new JLabel(message);
		this.setVisible(true);
		this.add(this.executeInfo);
		this.setBorder(BorderFactory.createBevelBorder(1));
		controller.addObserver(this);
	}

	public void setMessage(String message){
		this.executeInfo.setText(message);
	}


	@Override
	public void simulatorError(int time, RoadMap map, List<Event> event, SimulationError e) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				StatusBarPanel.this.executeInfo.setText("Simulation error at time " + time + "!");
			}
		});
	}

	@Override
	public void advance(int time, RoadMap map, List<Event> events) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				StatusBarPanel.this.executeInfo.setText("Step: " + time + " of simulation");
			}
		});
<<<<<<< HEAD:src/view/panel/StatusBarPanel.java
=======

>>>>>>> 4521dedd9b96a75715cb3d1a49e69b16d7a72290:src/view/StatusBarPanel.java
	}

	@Override
	public void addEvent(int time, RoadMap map, List<Event> events) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				StatusBarPanel.this.executeInfo.setText("Event added to the simulator at time " + time);
			}
		});
	}

	@Override
	public void reset(int time, RoadMap map, List<Event> events) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				StatusBarPanel.this.executeInfo = new JLabel("Reset has been done!");
			}
		});
	}

	@Override
	public void removeEvent(int time, RoadMap map, List<Event> events) {
		// empty, its not necessary to be implemented
	}

}
