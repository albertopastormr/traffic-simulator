package event;

import error.EventException;
import logic.Bicycle;
import logic.GenericJunction;
import logic.Vehicle;

import java.util.List;

public class EventNewBicycle extends EventNewVehicle {
    public EventNewBicycle(int time, String id, int speedMax, String[] itinerary) {
        super(time, id, speedMax, itinerary);
    }

    @Override
    protected Vehicle createVehicle(List<GenericJunction<?>> itinerary) throws EventException {
        return new Bicycle(this.id, this.speedMax, itinerary);
    }

    @Override
    public String toString() {
        return "New Bicycle";
    }
}
