package event;

import error.EventException;
import logic.Car;
import logic.GenericJunction;
import logic.Vehicle;

import java.util.List;

public class EventNewCar extends EventNewVehicle {
    public EventNewCar(int time, String id, int speedMax, String[] itinerary) {
        super(time, id, speedMax, itinerary);
    }

    @Override
    protected Vehicle createVehicle(List<GenericJunction<?>> itinerary) throws EventException {
        return new Car(this.id, this.speedMax, itinerary);
    }

    @Override
    public String toString() {
        return "New Car";
    }
}
