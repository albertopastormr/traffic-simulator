package view.dialog;

import control.Controller;
import error.SimulationError;
import event.Event;
import logic.*;
import view.MainWindow;
import view.observer.ObserverButton;
import view.observer.ObserverTrafficSimulator;
import view.panel.ButtonsPanel;
import view.panel.InformationPanel;
import view.panel.SimulationObjectPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ReportsDialog extends JDialog implements ObserverTrafficSimulator, ObserverButton {

	private MainWindow mainWindow;
	private ButtonsPanel buttonsPanel;
	private SimulationObjectPanel<Vehicle> vehiclesPanel;
	private SimulationObjectPanel<Road> roadsPanel;
	private SimulationObjectPanel<GenericJunction<?>> junctionsPanel;
	public static char	CLEAN_KEY = 'C';
	private static String informationText = "Select items for which you want to generate reports.\nUse 'c' to clear your selection.\nUse Ctrl+A to select all";

	public ReportsDialog(MainWindow mainWindow, Controller controller){
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		this.mainWindow = mainWindow;

		createInformationPanel(mainPanel);
		createCentralPanel(mainPanel);
		createButtonsPanel(mainPanel);

		this.add(mainPanel);
		controller.addObserver(this);

		setPreferredSize(new Dimension(1024, 300));
		setTitle("Generate Selected Reports");

		setModal(true);
		pack();
		setLocationRelativeTo(getParent());
	}
	public void showReportsDialog(){
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

	private void createInformationPanel(JPanel mainPanel){
		InformationPanel panelInfo = new InformationPanel(informationText);
		mainPanel.add(panelInfo);
	}
	private void createCentralPanel(JPanel mainPanel){
		JPanel centralPanel = new JPanel();
		this.vehiclesPanel = new SimulationObjectPanel<Vehicle>("Vehicles");
		this.roadsPanel = new SimulationObjectPanel<Road>("Roads");
		this.junctionsPanel = new SimulationObjectPanel<GenericJunction<?>>("Junctions");
		centralPanel.add(this.vehiclesPanel);
		centralPanel.add(this.roadsPanel);
		centralPanel.add(this.junctionsPanel);
		mainPanel.add(centralPanel);

	}
	private void createButtonsPanel(JPanel mainPanel){
		this.buttonsPanel = new ButtonsPanel(new String[]{"Cancel", "Generate"},this);
		mainPanel.add(this.buttonsPanel);
	}
	@Override
	public void simulatorError(int time, RoadMap map, List<Event> event, SimulationError e) {
		setVisible(false);
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
	public void removeEvent(int time, RoadMap map, List<Event> events) { this.setMap(map); }

	@Override
	public void executeButton(String button_tag) {
		switch(button_tag){
			case"Generate":
				this.mainWindow.generateSelectedItemsReport();
				this.mainWindow.setVisibleReportsDialog(false);
				break;
			case"Cancel":
				this.mainWindow.setVisibleReportsDialog(false);
				break;
		}
	}
}
