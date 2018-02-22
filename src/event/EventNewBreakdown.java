package event;

import error.RoadMapException;
import logic.Junction;
import logic.RoadMap;
import util.RoadParser;

import java.util.List;

public class EventNewBreakdown extends Event {
    protected int duration;
    protected String[] vehicles;

    public EventNewBreakdown(int time, int duration, String[] vehicles) {
        super(time);
        this.duration = duration;
        this.vehicles = vehicles;
    }
    @Override
    public void execute(RoadMap map) throws RoadMapException {


    }
    @Override
    public String toString() {
        return ""; // Por completar
    }
}