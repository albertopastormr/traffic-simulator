package view;

import error.SimulationError;
import event.Event;
import logic.RoadMap;

import java.util.List;

public interface ObserverTrafficSimulator {

    // notifica errores
    void simulatorError(int time, RoadMap map, List<Event> event, SimulationError e);

    // notifica el avance de los objetos de simulación
    void advance(int time, RoadMap map, List<Event> events);

    // notifica que se ha generado un nuevo evento
    void addEvent(int time, RoadMap map, List<Event> events);

    // notifica que la simulación se ha reiniciado
    void reset(int time, RoadMap map, List<Event> events);

}
