package logic;

import util.SortedArrayList;

import java.util.Comparator;
import java.util.List;

public class InRoad {

    protected Road road;
    protected List<Vehicle> vehiclesQueue;
    protected boolean trafficLight; // true=verde, false=rojo

    public InRoad(Road road) {
        this.road = road;
        this.vehiclesQueue = new SortedArrayList<>(Comparator.comparingInt(Vehicle::getLocationActual));
        this.trafficLight = false;
    }
}
