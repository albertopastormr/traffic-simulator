package view;

import control.Controller;
import error.SimulationError;
import event.Event;
import logic.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ReportsDialog extends JDialog implements ObserverTrafficSimulator {

	private ButtonsPanel buttonsPanel;
	private SimulationObjectPanel<Vehicle> vehiclesPanel;
	private SimulationObjectPanel<Road> roadsPanel;
	private SimulationObjectPanel<GenericJunction<?>> junctionsPanel;
	public static char	TECLA_LIMPIAR = 'C';

	public ReportsDialog(MainWindow mainWindow, Controller controller){
		// . . .
		this.vehiclesPanel = new SimulationObjectPanel<Vehicle>("Vehicles");
		this.roadsPanel = new SimulationObjectPanel<Road>("Roads");
		this.junctionsPanel = new SimulationObjectPanel<GenericJunction<?>>("Junctions");
		this.buttonsPanel = new ButtonsPanel(this);
		InformationPanel panelInfo = new InformationPanel();
		mainWindow.add(panelInfo, BorderLayout.PAGE_START);

		controller.addObserver(this); // Esto esta bien ???
		// . . .
	}
	public void show(){
		this.setVisible(true);
	}
	private void setMap(RoadMap roadMap){
		this.vehiclesPanel.setListModel(roadMap.getVehicles());
		this.roadsPanel.setListModel(roadMap.getRoads());
		this.junctionsPanel.setListModel(roadMap.getJunctions());
	}

	public List<Vehicle> getSelectedVehicles(){
		return this.vehiclesPanel.getSelectedItems();
	}
	public List<Road> getSelectedRoads(){
		return this.roadsPanel.getSelectedItems();
	}
	public List<GenericJunction<?>> getSelectedJunctions(){
		return this.junctionsPanel.getSelectedItems();
	}

	@Override
	public void simulatorError(int time, RoadMap map, List<Event> event, SimulationError e) {

	}

	@Override
	public void advance(int time, RoadMap map, List<Event> events) {
		this.setMap(map);
	}

	@Override
	public void addEvent(int time, RoadMap map, List<Event> events) {
		this.setMap(map);
	}

	@Override
	public void reset(int time, RoadMap map, List<Event> events) {
		this.setMap(map);
	}

	@Override
	public void removeEvent(int time, RoadMap map, List<Event> events) {

	}
}
