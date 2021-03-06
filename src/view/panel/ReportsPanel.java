package view.panel;

import control.Controller;
import error.SimulationError;
import event.Event;
import logic.RoadMap;
import view.observer.ObserverTrafficSimulator;

import java.util.List;

public class ReportsPanel extends TextAreaPanel implements ObserverTrafficSimulator {
    public ReportsPanel(String title, boolean editable, Controller controller){
        super(title, editable);
        controller.addObserver(this);
    }

    @Override
    public void simulatorError(int time, RoadMap map, List<Event> event, SimulationError e) {
        // Empty, does not need to be implemented
    }

    @Override
    public void advance(int time, RoadMap map, List<Event> event) {
        // Empty, does not need to be implemented
    }

    @Override
    public void addEvent(int time, RoadMap map, List<Event> event) {
        // Empty, does not need to be implemented
    }

    @Override
    public void reset(int time, RoadMap map, List<Event> event) {
        // Empty, does not need to be implemented
    }

    @Override
    public void removeEvent(int time, RoadMap map, List<Event> events) {
        // Empty, does not need to be implemented
    }

}
