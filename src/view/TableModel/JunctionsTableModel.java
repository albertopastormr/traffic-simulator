package view.TableModel;

import control.Controller;
import error.SimulationError;
import event.Event;
import logic.GenericJunction;
import logic.InRoad;
import logic.RoadMap;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class JunctionsTableModel extends TableModel<GenericJunction<?>> {
	public JunctionsTableModel(String[] columnIdJunction, Controller controller) {
		super(columnIdJunction, controller);
	}

	@Override
	public Object getValueAt(int row, int column) {
		Object s = null;
		switch (column){
			case 0: s = this.list.get(row).getId(); break;
			case 1: s = ( this.list.get(row).getGreenTrafficLightIndex() >= 0  ? this.list.get(row).getInRoads().get(this.list.get(row).getGreenTrafficLightIndex()).getRoad().getId() : ""); break;
			case 2:{
				s = "[";
				if(this.list.get(row).getGreenTrafficLightIndex() >= 0){
					for (InRoad inRoad : this.list.get(row).getInRoads())
						s += ( !inRoad.isTrafficLight() ? inRoad.getRoad().getId() : "") + " ";
				}
				s += "]";
			} break;
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
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JunctionsTableModel.this.fireTableStructureChanged();
			}
		});
	}

	@Override
	public void addEvent(int time, RoadMap map, List<Event> events) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JunctionsTableModel.this.list = map.getJunctions();
				JunctionsTableModel.this.fireTableStructureChanged();
			}
		});
	}

	@Override
	public void reset(int time, RoadMap map, List<Event> events) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JunctionsTableModel.this.list = new ArrayList<>();
				JunctionsTableModel.this.fireTableStructureChanged();
			}
		});
	}

	@Override
	public void removeEvent(int time, RoadMap map, List<Event> events) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JunctionsTableModel.this.fireTableStructureChanged();
			}
		});
	}
}
