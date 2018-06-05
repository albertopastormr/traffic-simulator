package model;

import error.EventException;
import error.NewEventException;
import error.RoadMapException;
import error.SimulationError;
import event.Event;
import logic.RoadMap;
import util.SortedArrayList;
import view.observer.Observer;
import view.observer.ObserverTrafficSimulator;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TrafficSimulator implements Observer<ObserverTrafficSimulator> {

    private RoadMap map;
    private List<Event> events;
    private int timeCount;
    private List<ObserverTrafficSimulator> observers;

    public TrafficSimulator() {
        this.map = new RoadMap();
        this.timeCount = 0;
        Comparator<Event> cmp = new Comparator<Event>() {

            @Override
            public int compare(Event o1, Event o2) {
                return Integer.compare(o1.getTime(), o2.getTime());
            }
        };
        this.events = new SortedArrayList<>(cmp); // Sorted by time
        this.observers = new ArrayList<>();
    }

    public void execute(int simulationStep, OutputStream fileOutput) throws SimulationError, IOException, EventException, NewEventException, RoadMapException {

        int timeLimit = this.timeCount + simulationStep - 1;
        try {
            while (this.timeCount <= timeLimit) {

                int i = 0, end = events.size();
                while (i < end) {
                    if (events.get(0).getTime() != this.timeCount) // events is sorted by time
                        break;
                    else {
                        events.get(0).execute(map);
                        events.remove(0); // Once it has been executed, we remove it from the list of events
                        notifyEventRemove();
                    }
                    i++;
                }

                map.update();
                notifyAdvance();

                if (fileOutput != null) {
                    fileOutput.write(map.generateReport(this.timeCount).getBytes());
                } else {
                    this.notifyError(new SimulationError("FileOutput unknown\n"));
                }
                this.timeCount++;
            }
        }
        catch(Exception ex){
            this.notifyError(new SimulationError(ex.getMessage()));
        }
    }
    public void reset(){
        this.map = new RoadMap();
        this.timeCount = 0;
        Comparator<Event> cmp = new Comparator<Event>() {

            @Override
            public int compare(Event o1, Event o2) {
                return Integer.compare(o1.getTime(), o2.getTime());
            }
        };
        this.events = new SortedArrayList<>(cmp); // Sorted by time
        notifyReset();
    }
    public void insertEvent(Event e) throws SimulationError {
        if(e != null){
            if(e.getTime() >= this.timeCount) { // The element e is inserted if it could be executed
                this.events.add(e);
                this.notifyNewEvent();
            }
            else{
                SimulationError err = new SimulationError("ERROR: insertEvent() did not work");
                this.notifyError(err);
                throw err;
            }
        }
        else{
            SimulationError err = new SimulationError("ERROR: insertEvent() did not work");
            this.notifyError(err);
            throw err;
        }

    }

    public void removeVehicle(String idVehicle){
        this.map.removeVehicle(idVehicle);
        notifyAdvance();
    }

    public String showAll(){
        return map.showAll();
    }

    public String generateReport(){
        return this.map.generateReport(this.timeCount);
    }
    private void notifyNewEvent(){
        for(ObserverTrafficSimulator obs : this.observers)
            obs.addEvent(this.timeCount, this.map, this.events);
    }
    private void notifyError(SimulationError error){
        for(ObserverTrafficSimulator obs : this.observers)
            obs.simulatorError(this.timeCount, this.map, this.events, error);
    }
    private void notifyReset(){
        for(ObserverTrafficSimulator obs : this.observers)
            obs.reset(this.timeCount, this.map, this.events);
    }
    private void notifyAdvance(){
        for(ObserverTrafficSimulator obs : this.observers)
            obs.advance(this.timeCount, this.map, this.events);
    }
    private void notifyEventRemove(){
        for(ObserverTrafficSimulator obs : this.observers)
            obs.removeEvent(this.timeCount, this.map, this.events);
    }
    @Override
    public void addObserver(ObserverTrafficSimulator o) {
        if(o != null && !this.observers.contains(o))
            this.observers.add(o);
    }

    @Override
    public void removeObserver(ObserverTrafficSimulator o) {
        if(o != null && this.observers.contains(o))
            this.observers.remove(o);
    }
}

