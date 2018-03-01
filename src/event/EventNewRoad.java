package event;

import error.EventException;
import error.RoadMapException;
import logic.GenericJunction;
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

        GenericJunction<?> originRoadJunction = map.getJunction(this.originJunctionId);
        GenericJunction<?> destinationRoadJunction = map.getJunction(this.destinationJunctionId);
        Road road = createRoad(originRoadJunction, destinationRoadJunction);
        map.addRoad(this.id, originRoadJunction, road, destinationRoadJunction);
    }

    protected Road createRoad(GenericJunction<?> originJunction, GenericJunction<?> destinationJunction){
       return new Road(this.id, this.length, this.speedMax, originJunction, destinationJunction);
    }
    @Override
    public String toString() {
        return "New Road";
    }

    // . . .
}