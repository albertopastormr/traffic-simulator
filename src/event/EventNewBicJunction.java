package event;

import logic.BicJunction;
import logic.GenericJunction;

public class EventNewBicJunction extends EventNewJunction {

	private int time_slice;

	public EventNewBicJunction(int time, String id, int time_slice) {
		super(time, id);
		this.time_slice = time_slice;
	}

	@Override
	protected GenericJunction<?> createJunction() {
		return new BicJunction(this.id, this.time_slice);
	}

	@Override
	public String toString() {
		return "New BicJunction";
	}
}
