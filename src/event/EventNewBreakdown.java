package event;

import error.EventException;
import error.RoadMapException;
import logic.Junction;
import logic.RoadMap;
import logic.Vehicle;
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
    public void execute(RoadMap map) throws RoadMapException, EventException {
        List<Vehicle> li = RoadParser.VehicleListParse(vehicles, map);
        for(Vehicle v : li)
            v.setBreakdownTime(duration);
    }
    @Override
    public String toString() {
        return "New Breakdown";
    }
}