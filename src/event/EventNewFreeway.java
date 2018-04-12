package event;

import logic.Freeway;
import logic.GenericJunction;
import logic.Road;

public class EventNewFreeway extends  EventNewRoad {

    protected Integer numLanes;

    public EventNewFreeway(Integer time, String id, Integer speedMax, Integer length, String originJunctionId, String destinationJunctionId, int numLanes) {
        super(time, id, speedMax, length, originJunctionId, destinationJunctionId);
        this.numLanes = numLanes;
    }

    protected Road createRoad(GenericJunction<?> originJunction, GenericJunction<?> destinationJunction){
        return new Freeway(this.id, this.length, this.speedMax, originJunction, destinationJunction, this.numLanes);
    }

    @Override
    public String toString() {
        return "New Freeway";
    }
}
