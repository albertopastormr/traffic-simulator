package event;

import logic.Junction;
import logic.RoadMap;

import java.util.List;

public class EventNewVehicle extends Event {
    protected String id;
    protected Integer speedMax;
    protected String[] itinerary;

    public EventNewVehicle(int time, String id, int speedMax, String[] itinery) {

    }
    @Override
    public void ejecuta(RoadMap map) {
        List<Junction> iti = RoadParser.parseaListaCruces(this.itinerario,map);
        // si iti es null o tiene menos de dos cruces lanzar excepción
        // en otro caso crear el vehículo y añadirlo al mapa.
    }
    @Override
    public String toString() {...}
}