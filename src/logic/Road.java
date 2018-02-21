package logic;

import error.EventException;
import ini.IniSection;
import util.SortedArrayList;

import java.util.Comparator;
import java.util.List;

public class Road  extends SimulationObject {

    protected int length;
    protected int speedMax;
    protected Junction origin;
    protected Junction destination;
    protected List<Vehicle> vehicles;

    protected Comparator<Vehicle> vehicleComparator;

    public Road(String id,  int length, int maxSpeed, Junction source, Junction destination) {
        super(id);
        this.length = length;
        this.speedMax = maxSpeed;
        this.origin = source;
        this.destination = destination;

        vehicleComparator = new Comparator<Vehicle>() {
            @Override
            public int compare(Vehicle o1, Vehicle o2) {
                return o1.getLocationActual() - o2.getLocationActual();
            }
        };
        vehicles = new SortedArrayList<Vehicle>(vehicleComparator);
    }


    public void inVehicle(Vehicle vehicle){
        if(!vehicles.contains(vehicle)){
            vehicles.add(vehicle);
            vehicles.sort(vehicleComparator);
        }
    }
    public void outVehicle(Vehicle vehicle) throws EventException {
        if(!vehicles.remove(vehicle))
            throw new EventException("Uncompleted delete: vehicle" + vehicle.id + " does not exist in road " + this.id + "\n");
    }
    public void vehicleToJunction(Vehicle vehicle){
        this.destination.inVehicleToJunction(this.id , vehicle);
    }


    @Override
    public void completeSectionDetails(IniSection is) {

    }

    @Override
    public String getSectionName() {
        return null;
    }

    public void advance(){

    }
}
