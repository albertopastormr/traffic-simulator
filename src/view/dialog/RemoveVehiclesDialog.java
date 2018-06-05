package view;

import control.Controller;
import error.SimulationError;
import event.Event;
import logic.RoadMap;
import logic.Vehicle;
import view.observer.ObserverButton;
import view.observer.ObserverTrafficSimulator;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RemoveVehiclesDialog extends JDialog implements ObserverTrafficSimulator, ObserverButton {

	private MainWindow mainWindow;
	private ButtonsPanel buttonsPanel;
	private SimulationObjectPanel<Vehicle> vehiclesPanel;

	public RemoveVehiclesDialog(MainWindow mainWindow, Controller controller){
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		this.mainWindow = mainWindow;

		this.buttonsPanel = new ButtonsPanel(new String[]{"OK", "CANCEL"}, this);
		this.vehiclesPanel = new SimulationObjectPanel<>("Vehicle Remover");

		mainPanel.add(this.vehiclesPanel);
		mainPanel.add(this.buttonsPanel);

		this.add(mainPanel);
		controller.addObserver(this);

		setPreferredSize(new Dimension(1024, 300));
		setTitle("Remove Selected Vehicles");

		setModal(true);
		pack();
		setLocationRelativeTo(getParent());
	}

	@Override
	public void executeButton(String button_tag) {
		switch (button_tag){
			case "OK":{
				mainWindow.removeSelectedVehicles();
				this.setVisible(false);
			} break;
			case "CANCEL":{
				this.setVisible(false);
			} break;
		}
	}

	private void setMap(RoadMap roadMap){
		this.vehiclesPanel.setListModel(roadMap.getVehicles());
	}

	public List<Vehicle> getSelectedVehicles(){
		return this.vehiclesPanel.getSelectedItems();
	}


	@Override
	public void simulatorError(int time, RoadMap map, List<Event> event, SimulationError e) {
		//
	}

	@Override
	public void advance(int time, RoadMap map, List<Event> events) {
		this.setMap(map);
	}

	@Override
	public void addEvent(int time, RoadMap map, List<Event> events) {
		//
	}

	@Override
	public void reset(int time, RoadMap map, List<Event> events) {
		this.setMap(map);
	}

	@Override
	public void removeEvent(int time, RoadMap map, List<Event> events) {
		//
	}
}
