package logic;

import error.EventException;
import ini.IniSection;
import util.SortedArrayList;

import java.util.Comparator;
import java.util.List;

public class Road  extends SimulationObject {

    protected int length;
    protected int speedMax;
    protected GenericJunction<?> origin;
    protected GenericJunction<?> destination;
    protected List<Vehicle> vehicles;

    protected Comparator<Vehicle> vehicleComparator;

    public Road(String id,  int length, int maxSpeed, GenericJunction<?> source, GenericJunction<?> destination) {
        super(id);
        this.length = length;
        this.speedMax = maxSpeed;
        this.origin = source;
        this.destination = destination;

        this.vehicleComparator = new Comparator<Vehicle>() {
            @Override
            public int compare(Vehicle o1, Vehicle o2) {
                return o2.getLocationActual() - o1.getLocationActual();
            }
        };
        this.vehicles = new SortedArrayList<Vehicle>(vehicleComparator);
    }


    public void inVehicle(Vehicle vehicle) throws EventException {
        if(!vehicles.contains(vehicle)){
            vehicles.add(vehicle);
            vehicles.sort(vehicleComparator);
        }
        else
            throw new EventException("Uncompleted add: vehicle" + vehicle.id + " already exist in road: " + this.id + "\n");
    }
    public void outVehicle(Vehicle vehicle) throws EventException {
        if(!vehicles.remove(vehicle))
            throw new EventException("Uncompleted delete: vehicle" + vehicle.id + " does not exist in road: " + this.id + "\n");
    }
    public void vehicleToJunction(Vehicle vehicle){
        this.destination.inVehicleToJunction(this.id , vehicle);
    }


    @Override
    public void completeSectionDetails(IniSection is) {
        if(this.vehicles.size() == 0)
            is.setValue("state", "");
        else{
            String s = "";
            s += this.vehicles.get(0);
            for(int i = 1; i < this.vehicles.size(); i++)
                s += "," +  this.vehicles.get(i).toString();
            is.setValue("state", s);
        }
    }

    @Override
    public String getSectionName() {
        return "road_report";
    }

    public void advance(){
        int speed = calculateSpeed();
        int obstacles = 0;

        for(int i = 0; i < this.vehicles.size(); i++){
            Vehicle v = this.vehicles.get(i);
            if(v.getBreakdownTime() > 0)
                obstacles++;
            if(v.getBreakdownTime() == 0)
                v.setSpeedActual(speed/this.calculateMarkdownFactor(obstacles));
            v.advance();
        }
        this.vehicles.sort(this.vehicleComparator);
    }

    protected int calculateSpeed(){
        int newPossibleSpeed = (this.speedMax/ (this.vehicles.size() < 1 ? 1 : this.vehicles.size())) + 1;
        return (this.speedMax <= newPossibleSpeed ?  this.speedMax : newPossibleSpeed);
    }
    protected int calculateMarkdownFactor(int obstacles){
        return (obstacles == 0 ? 1 : 2);
    }

    public int getLength() {
        return length;
    }

    public GenericJunction<?> getOrigin() {
        return origin;
    }

    public GenericJunction<?> getDestination() {
        return destination;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }
}
