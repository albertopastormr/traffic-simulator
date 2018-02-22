package event;

import error.EventException;
import error.NewEventException;
import error.RoadMapException;
import logic.RoadMap;


public abstract class Event {

    protected Integer time;

    public Event(Integer time) {
        this.time = time;
    }

    public Integer getTime() {
        return time;
    }

    public abstract void execute(RoadMap map) throws NewEventException, EventException, RoadMapException;
}
