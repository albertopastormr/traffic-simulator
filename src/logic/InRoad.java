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
        this.vehiclesQueue = new SortedArrayList<>(Comparator.comparing(Vehicle::getLocationActual));
        this.trafficLight = false;
    }

    public boolean isTrafficLight() {
        return trafficLight;
    }

    void setTrafficLight(boolean colour){
        this.trafficLight = colour;
    }

    void addVehicleToQueue(Vehicle vehicle){
        this.vehiclesQueue.add(vehicle);
    }

    void moveFirstVehicle() throws EventException {
        if(vehiclesQueue.size() > 0) {
            this.vehiclesQueue.get(0).moveNextRoad();
            this.vehiclesQueue.remove(0);
        }
    }

    public int numberOfBicyclesAtQueue(){
        int ret = 0;
        for (Vehicle v : vehiclesQueue){
            if(v instanceof Bicycle)
                ret++;
        }
        return ret;
    }

    private void copyVehiclesQueueFromRoad(Road road){
        for(Vehicle v : road.vehicles)
            this.addVehicleToQueue(v);
    }

    public Road getRoad() {
        return road;
    }

    @Override
    public String toString() {
        String s = "(" + this.road.getId() +","+ (this.trafficLight ? "green" : "red") + ",";
        s += "[";
        if(this.vehiclesQueue.size() > 0) {
            s += this.vehiclesQueue.get(0).getId();
            for (int i = 1; i < this.vehiclesQueue.size(); i++) {
                s += "," + this.vehiclesQueue.get(i).getId();
            }
        }
        s += "])";
        return s;
    }
}
