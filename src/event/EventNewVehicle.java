package event;

import logic.Junction;
import logic.RoadMap;
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
    public void execute(RoadMap map) {
        List<Junction> iti = RoadParser.JunctionListParse(this.itinerary,map);
        // si iti es null o tiene menos de dos cruces lanzar excepción
        // en otro caso crear el vehículo y añadirlo al mapa.
    }
    @Override
    public String toString() {
        return time + id + speedMax + itinerary; // Por completar
    }
}