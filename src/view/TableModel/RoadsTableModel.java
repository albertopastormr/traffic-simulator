package view.TableModel;

import control.Controller;
import error.SimulationError;
import event.Event;
import logic.Road;
import logic.RoadMap;

import java.util.ArrayList;
import java.util.List;

public class RoadsTableModel extends TableModel<Road> {
	public RoadsTableModel(String[] columnIdRoad, Controller controller) {
		super(columnIdRoad, controller);
	}

	@Override
	public Object getValueAt(int row, int column) {
		Object s = null;
		switch (column){
			case 0: s = this.list.get(row).getId(); break;
			case 1: s = this.list.get(row).getOrigin(); break;
			case 2: s = this.list.get(row).getDestination(); break;
			case 3: s = this.list.get(row).getLength(); break;
			case 4: s = this.list.get(row).getSpeedMax(); break;
			case 5: s = this.list.get(row).getVehicles(); break;
			default: assert (false);
		}
		return s;
	}

	@Override
	public void simulatorError(int time, RoadMap map, List<Event> event, SimulationError e) {
		// empty, its not necessary to be implemented
	}

	@Override
	public void advance(int time, RoadMap map, List<Event> events) {
		this.fireTableStructureChanged();
	}

	@Override
	public void addEvent(int time, RoadMap map, List<Event> events) {
		this.list = map.getRoads();
		this.fireTableStructureChanged();
	}

	@Override
	public void reset(int time, RoadMap map, List<Event> events) {
		this.list = new ArrayList<>();
		this.fireTableStructureChanged();
	}

	@Override
	public void removeEvent(int time, RoadMap map, List<Event> events) {
		this.fireTableStructureChanged();
	}
}
