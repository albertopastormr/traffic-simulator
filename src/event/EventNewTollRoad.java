package event;

import logic.GenericJunction;
import logic.Road;
import logic.TollRoad;

public class EventNewTollRoad extends EventNewRoad {

	private int toll_cost;

	public EventNewTollRoad(Integer time, String id, Integer speedMax, Integer length, String originJunctionId, String destinationJunctionId, int toll_cost) {
		super(time, id, speedMax, length, originJunctionId, destinationJunctionId);
		this.toll_cost = toll_cost;
	}

	protected Road createRoad(GenericJunction<?> originJunction, GenericJunction<?> destinationJunction){
		return new TollRoad(this.id, this.length, this.speedMax, originJunction, destinationJunction, toll_cost);
	}

	@Override
	public String toString() {
		return "New TollRoad";
	}
}
