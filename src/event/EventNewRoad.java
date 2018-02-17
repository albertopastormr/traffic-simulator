package event;

import logic.RoadMap;

public class EventNewRoad extends Event {

    protected String id;
    protected Integer speedMax;
    protected Integer length;
    protected String originJunctionId;
    protected String destinyJunctionId;

    public EventNewRoad(Integer time, String id, Integer speedMax, Integer length, String originJunctionId, String destinyJunctionId) {
        super(time);
        this.id = id;
        this.speedMax = speedMax;
        this.length = length;
        this.originJunctionId = originJunctionId;
        this.destinyJunctionId = destinyJunctionId;
    }

    @Override
    public void execute(RoadMap map) {
        // Por completar
    }
    @Override
    public String toString() {
        return time + id ; // Por completar
    }
}