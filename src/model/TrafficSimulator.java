package model;

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
        this.events = new SortedArrayList<>(cmp); // estructura ordenada por “tiempo”
    }

    public void execute(int simulationStep, OutputStream fileOutput) throws SimulationError, IOException {

        int timeLimit = this.timeCount + simulationStep - 1;
        while (this.timeCount <= timeLimit) {

            for(Event e : events){
                if(e.getTime() == this.timeCount)
                    e.execute(map);
            }
            map.update();
            if(fileOutput != null) {
                PrintStream printStream = new PrintStream(fileOutput);
                printStream.print(map.generateReport(this.timeCount));
            }
            else
                throw new SimulationError("FileOutput unknown\n");
        }
    }
    public void insertEvent(Event e){
        if(e.getTime() >= this.timeCount)
            this.events.add(e);
    }
}

