package view;

import control.Controller;
import logic.Vehicle;
import view.TableModel.BrVehiclesTableModel;
import view.TableModel.VehiclesTableModel;
import view.observer.ObserverButton;

import javax.swing.*;
import java.awt.*;

public class BrVehiclesDialog extends JDialog implements  ObserverButton {

	private MainWindow mainWindow;
	private ButtonsPanel buttonsPanel;
	private TablePanel<Vehicle> panelVehicles;

	public BrVehiclesDialog(MainWindow mainWindow, Controller controller){
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		this.mainWindow = mainWindow;

		this.panelVehicles = new TablePanel<Vehicle>("BrVehicles", new BrVehiclesTableModel(new String[]{"ID","BR.TIME"}, controller));

		mainPanel.add(this.panelVehicles);

		this.buttonsPanel = new ButtonsPanel(new String[]{"OK"}, this);
		mainPanel.add(this.buttonsPanel);
		this.add(mainPanel);

		setPreferredSize(new Dimension(1024, 300));
		setTitle("BrVehicles dialog");

		setModal(true);
		pack();
		setLocationRelativeTo(getParent());
	}

	@Override
	public void executeButton(String button_tag) {
		switch (button_tag){
			case "OK":{
				this.setVisible(false);
			} break;
		}
	}
}
