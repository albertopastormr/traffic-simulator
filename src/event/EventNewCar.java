package event;

import error.EventException;
import logic.Car;
import logic.GenericJunction;
import logic.Vehicle;

import java.util.List;

public class EventNewCar extends EventNewVehicle {

    private int resistance;
    private double faultProbability;
    private int maxFaultDuration;
    private long seed;

    public EventNewCar(int time, String id, int speedMax, String[] itinerary, int resistance, double faultProbability, int maxFaultDuration, long seed) {
        super(time, id, speedMax, itinerary);
        this.resistance = resistance;
        this.faultProbability = faultProbability;
        this.maxFaultDuration = maxFaultDuration;
        this.seed = seed;
    }

    @Override
    protected Vehicle createVehicle(List<GenericJunction<?>> itinerary) throws EventException {
        return new Car(this.id, this.speedMax, this.resistance, this.faultProbability, this.seed,this.maxFaultDuration,  itinerary);
    }

    @Override
    public String toString() {
        return "New Car";
    }
}
