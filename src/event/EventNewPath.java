package event;

import logic.GenericJunction;
import logic.Path;
import logic.Road;

public class EventNewPath extends EventNewRoad {
    public EventNewPath(Integer time, String id, Integer speedMax, Integer length, String originJunctionId, String destinationJunctionId) {
        super(time, id, speedMax, length, originJunctionId, destinationJunctionId);
    }

    @Override
    protected Road createRoad(GenericJunction<?> originJunction, GenericJunction<?> destinationJunction) {
        return new Path(this.id, this.length, this.speedMax, originJunction, destinationJunction);
    }

    // . . .
}
