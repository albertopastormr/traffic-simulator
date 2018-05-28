package view.TableModel;

import control.Controller;
import error.SimulationError;
import event.Event;
import logic.RoadMap;

import javax.swing.*;
import java.util.ArrayList;
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
        // empty, its not necessary to be implemented
    }

    @Override
    public void advance(int time, RoadMap map, List<Event> events) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                EventsTableModel.this.fireTableStructureChanged();
            }
        });
    }

    @Override
    public void addEvent(int time, RoadMap map, List<Event> events) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                EventsTableModel.this.list = events;
                EventsTableModel.this.fireTableStructureChanged();
            }
        });
    }

    @Override
    public void reset(int time, RoadMap map, List<Event> events) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                EventsTableModel.this.list = new ArrayList<>();
                EventsTableModel.this.fireTableStructureChanged();
            }
        });
    }

    @Override
    public void removeEvent(int time, RoadMap map, List<Event> events) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                EventsTableModel.this.list = events;
                EventsTableModel.this.fireTableStructureChanged();
            }
        });
    }

}
