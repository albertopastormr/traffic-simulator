package event;

import logic.RoadMap;

public abstract class Event {

    protected Integer time;

    public Event(Integer time) {
        this.time = time;
    }

    public Integer getTime() {
        return time;
    }

    public abstract void execute(RoadMap map);
}
