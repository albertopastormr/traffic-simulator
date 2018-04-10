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
		this.setLayout(new GridLayout(3,1));
		InformationPanel panelInfo = new InformationPanel();
		mainWindow.add(panelInfo, BorderLayout.PAGE_START);

		createCentralPanel();
		createButtonsPanel();

		controller.addObserver(this);
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

	private void createCentralPanel(){
		JPanel centralPanel = new JPanel();
		this.vehiclesPanel = new SimulationObjectPanel<Vehicle>("Vehicles");
		this.roadsPanel = new SimulationObjectPanel<Road>("Roads");
		this.junctionsPanel = new SimulationObjectPanel<GenericJunction<?>>("Junctions");
		centralPanel.add(this.vehiclesPanel);
		centralPanel.add(this.roadsPanel);
		centralPanel.add(this.junctionsPanel);
		this.add(centralPanel);

	}
	private void createButtonsPanel(){
		this.buttonsPanel = new ButtonsPanel(this);

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
