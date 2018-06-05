package event;

import error.EventException;
import logic.GenericJunction;
import logic.Tank;
import logic.Vehicle;

import java.util.List;

public class EventNewTank extends EventNewVehicle {

	private int numberOfMissiles;

	public EventNewTank(int time, String id, int speedMax, String[] itinerary, int numberOfMissiles) {
		super(time, id, speedMax, itinerary);
		this.numberOfMissiles = numberOfMissiles;
	}

	@Override
	protected Vehicle createVehicle(List<GenericJunction<?>> itinerary) throws EventException {
		return new Tank(this.id, this.speedMax, itinerary, this.numberOfMissiles);
	}

	@Override
	public String toString() {
		return "New Tank";
	}
}
