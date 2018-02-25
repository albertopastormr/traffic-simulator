package logic;

import error.EventException;
import util.SortedArrayList;

import java.util.Comparator;
import java.util.List;

public class InRoad {

    protected Road road;
    protected List<Vehicle> vehiclesQueue;
    protected boolean trafficLight; // true = green , false = red

    public InRoad(Road road) {
        this.road = road;
        this.vehiclesQueue = road.vehicles;
        this.trafficLight = false;
    }

    void setTrafficLight(boolean colour){
        this.trafficLight = colour;
    }

    public void addVehicleToQueue(Vehicle vehicle){
        this.vehiclesQueue.add(vehicle);
    }

    public void moveFirstVehicle() throws EventException {
        if(vehiclesQueue.size() > 0)
            this.vehiclesQueue.get(0).moveNextRoad();
    }

    private void copyVehiclesQueueFromRoad(Road road){
        for(Vehicle v : road.vehicles)
            this.addVehicleToQueue(v);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
