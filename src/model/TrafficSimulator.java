package model;

import error.EventException;
import error.NewEventException;
import error.RoadMapException;
import error.SimulationError;
import event.Event;
import logic.RoadMap;
import util.SortedArrayList;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Comparator;
import java.util.List;

public class TrafficSimulator {

    private RoadMap map;
    private List<Event> events;
    private int timeCount;

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
    }

    public void execute(int simulationStep, OutputStream fileOutput) throws SimulationError, IOException, EventException, NewEventException, RoadMapException {

        int timeLimit = this.timeCount + simulationStep - 1;
        while (this.timeCount <= timeLimit) {

            int i = 0;
            while(i < events.size()){
                if(events.get(i).getTime() != this.timeCount) // events is sorted by time
                    break;
                else {
                    events.get(i).execute(map);
                    events.remove(i); // Once it has been executed, we remove it from the list of events
                }
                i++;
            }

            map.update();
            if(fileOutput != null) {
                PrintStream printStream = new PrintStream(fileOutput);
                printStream.print(map.generateReport(this.timeCount));
            }
            else
                throw new SimulationError("FileOutput unknown\n");
            this.timeCount++;
        }
    }
    public void insertEvent(Event e){
        if(e.getTime() >= this.timeCount) // The element e is inserted if it could be executed
            this.events.add(e);
    }
}

