package event;

import error.EventException;
import error.RoadMapException;
import logic.Junction;
import logic.Road;
import logic.RoadMap;

public class EventNewRoad extends Event {

    protected String id;
    protected Integer speedMax;
    protected Integer length;
    protected String originJunctionId;
    protected String destinationJunctionId;

    public EventNewRoad(Integer time, String id, Integer speedMax, Integer length, String originJunctionId, String destinationJunctionId) {
        super(time);
        this.id = id;
        this.speedMax = speedMax;
        this.length = length;
        this.originJunctionId = originJunctionId;
        this.destinationJunctionId = destinationJunctionId;
    }

    @Override
    public void execute(RoadMap map) throws RoadMapException, EventException {

        Junction originRoadJunction = map.getJunction(this.originJunctionId);
        Junction destinationRoadJunction = map.getJunction(this.destinationJunctionId);
        Road road = new Road(this.id, this.length, this.speedMax, originRoadJunction, destinationRoadJunction);
        map.addRoad(this.id, originRoadJunction, road, destinationRoadJunction);
    }
    @Override
    public String toString() {
        return time + id ; // Por completar
    }
}