package view;

import control.Controller;
import error.SimulationError;
import event.Event;
import logic.*;
import view.observer.ObserverTrafficSimulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ReportsDialog extends JDialog implements ObserverTrafficSimulator {

	private ButtonsPanel buttonsPanel;
	private SimulationObjectPanel<Vehicle> vehiclesPanel;
	private SimulationObjectPanel<Road> roadsPanel;
	private SimulationObjectPanel<GenericJunction<?>> junctionsPanel;
	public static char	CLEAN_KEY = 'C';
	private static String informationText = "Select items for which you want to generate reports.\nUse 'c' to clear your selection.\nUse Ctrl+A to select all";

	public ReportsDialog(MainWindow mainWindow, Controller controller){
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

		createInformationPanel(mainPanel);
		createCentralPanel(mainPanel);
		createButtonsPanel(mainWindow, mainPanel);

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
	private void createButtonsPanel(MainWindow mainWindow, JPanel mainPanel){
		this.buttonsPanel = new ButtonsPanel();
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.setVisibleReportsDialog(false);
			}
		});
		JButton generateButton = new JButton("Generate");
		generateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.generateSelectedItemsReport();
				mainWindow.setVisibleReportsDialog(false);
			}
		});

		this.buttonsPanel.add(cancelButton);
		this.buttonsPanel.add(generateButton);

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
	public void removeEvent(int time, RoadMap map, List<Event> events) {

	}
}
