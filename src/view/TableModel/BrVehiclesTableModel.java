package view.TableModel;

import control.Controller;
import error.SimulationError;
import event.Event;
import logic.RoadMap;
import logic.Vehicle;
import view.BrVehiclesDialog;
import view.MainWindow;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class BrVehiclesTableModel extends TableModel<Vehicle>  {


	public BrVehiclesTableModel(String[] columnIdEvents, Controller controller) {
		super(columnIdEvents, controller);
	}

	@Override
	public Object getValueAt(int row, int column) {
		Object s = null;
		switch (column){
			case 0: s = this.list.get(row).getId(); break;
			case 1: s = this.list.get(row).getBreakdownTime(); break;
			case 2: s = this.list.get(row).getLocationActual(); break;
			case 3: s = this.list.get(row).getSpeedActual(); break;
			case 4: s = this.list.get(row).getKilometrage(); break;
			case 5: s = this.list.get(row).getRoadActual(); break;
			case 6: s = this.list.get(row).getItinerary(); break;
			default: assert (false);
		}
		return s;
	}

	@Override
	public void simulatorError(int time, RoadMap map, List<Event> event, SimulationError e) {
		// empty
	}

	@Override
	public void advance(int time, RoadMap map, List<Event> events) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				BrVehiclesTableModel.this.list = new ArrayList<>();
				for(Vehicle v : map.getVehicles()){
					if(v.getBreakdownTime() > 0)
						BrVehiclesTableModel.this.list.add(v);
				}
				BrVehiclesTableModel.this.fireTableStructureChanged();
			}
		});

	}

	@Override
	public void addEvent(int time, RoadMap map, List<Event> events) {
		//
	}

	@Override
	public void reset(int time, RoadMap map, List<Event> events) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				BrVehiclesTableModel.this.list = new ArrayList<>();
				BrVehiclesTableModel.this.fireTableStructureChanged();
			}
		});
	}

	@Override
	public void removeEvent(int time, RoadMap map, List<Event> events) {
		//
	}
}
