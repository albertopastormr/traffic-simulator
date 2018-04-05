package view;

import control.Controller;
import error.SimulationError;
import event.Event;
import logic.RoadMap;

import java.util.List;

public class EventsModelTable extends TableModel<Event> {

    public EventsModelTable(String[] columnIdEvents, Controller controller){

    }

    @Override
    public Object getValueAt(int row, int column) {
        Object s = null;
        switch (column){
            case 0: s = row; break;
            case 1: s = this.list.get(row).getTime(); break;
            case 2: s = this.list.get(row).toString(); break;
            default: assert (false);
        }
        return s;
    }

    @Override
    public void simulatorError(int time, RoadMap map, List<Event> event, SimulationError e) {

    }

    @Override
    public void advance(int time, RoadMap map, List<Event> event) {

    }

    @Override
    public void addEvent(int time, RoadMap map, List<Event> event) {

    }

    @Override
    public void reset(int time, RoadMap map, List<Event> event) {

    }
}
