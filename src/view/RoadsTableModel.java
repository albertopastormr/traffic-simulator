package view;

import control.Controller;
import error.SimulationError;
import event.Event;
import logic.Road;
import logic.RoadMap;

import java.util.List;

public class RoadsTableModel extends TableModel<Road> {
	public RoadsTableModel(String[] columnIdRoad, Controller controller) {
		super(columnIdRoad, controller);
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
