package event;

import error.EventException;
import error.NewEventException;
import error.RoadMapException;
import logic.Junction;
import logic.RoadMap;
import logic.Vehicle;
import util.RoadParser;

import java.util.List;

public class EventNewVehicle extends Event {
    protected String id;
    protected Integer speedMax;
    protected String[] itinerary;

    public EventNewVehicle(int time, String id, int speedMax, String[] itinerary) {
        super(time);
        this.id = id;
        this.speedMax = speedMax;
        this.itinerary = itinerary;
    }
    @Override
    public void execute(RoadMap map) throws NewEventException, EventException, RoadMapException {

        List<Junction> iti = RoadParser.JunctionListParse(this.itinerary,map);

        if(iti == null || iti.size() < 2)
            throw new NewEventException("Itinerary for NewVehicle: " + this.id +" not valid\n");
        else{
            Vehicle vehicle = new Vehicle(this.id, this.speedMax, iti);
            map.addVehicle(this.id, vehicle);
        }
    }
    @Override
    public String toString() {
        return time + id + speedMax + itinerary; // Por completar
    }
}