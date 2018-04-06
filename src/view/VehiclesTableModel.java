package view;

import control.Controller;
import error.SimulationError;
import event.Event;
import logic.RoadMap;
import logic.Vehicle;

import java.util.List;

public class VehiclesTableModel extends TableModel<Vehicle> {
	public VehiclesTableModel(String[] columnIdVehicle, Controller controller) {
		super(columnIdVehicle, controller);
	}


	@Override
	public void simulatorError(int time, RoadMap map, List<Event> event, SimulationError e) {

	}

	@Override
	public void advance(int time, RoadMap map, List<Event> events) {

	}

	@Override
	public void addEvent(int time, RoadMap map, List<Event> events) {

	}

	@Override
	public void reset(int time, RoadMap map, List<Event> events) {

	}
}
