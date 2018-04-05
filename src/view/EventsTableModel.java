package view;

import control.Controller;
import error.SimulationError;
import event.Event;
import logic.RoadMap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EventsTableModel extends TableModel<Event> {

    public EventsTableModel(String[] columnIdEvents, Controller controller){
        super(columnIdEvents,controller);

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
        // PENDIENTE
    }

    @Override
    public void advance(int time, RoadMap map, List<Event> events) {
        this.fireTableStructureChanged();
    }

    @Override
    public void addEvent(int time, RoadMap map, List<Event> events) {
        this.list.clear(); // Metodo pendiente revision
        this.list.addAll(events);
        this.columnIds[this.columnIds.length] = String.valueOf(this.columnIds.length);
    }

    @Override
    public void reset(int time, RoadMap map, List<Event> events) {
        this.columnIds =null;
        this.list = new ArrayList<>();
    }
}
