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
                return o2.getLocationActual() - o1.getLocationActual();
            }
        };
        vehicles = new SortedArrayList<Vehicle>(vehicleComparator);
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
            is.setValue("queues", "");
        else{
            String s = "";
            for(int i = 0; i < this.vehicles.size(); i++)
                s += "," +  this.vehicles.get(i).toString();
            is.setValue("vehicles", s);
        }
    }

    @Override
    public String getSectionName() {
        return "road_report";
    }

    public void advance(){
        int speed = calculateSpeed();
        int obstacles = 0;

        /*for (Vehicle v : this.vehicles) { Bucle for-each cambiado por for convencional porque este lanzaba ConcurrentModificationException
            if(v.getBreakdownTime() > 0)
                obstacles++;
            v.setSpeedActual(speed/this.calculateMarkdownFactor(obstacles));
            v.advance();
        }*/
        for(int i = 0; i < this.vehicles.size(); i++){
            Vehicle v = this.vehicles.get(i);
            if(v.getBreakdownTime() > 0)
                obstacles++;
            if(v.getBreakdownTime() == 0) // Este condicional podria dar error
                v.setSpeedActual(speed/this.calculateMarkdownFactor(obstacles));
            v.advance();
        }
        this.vehicles.sort(this.vehicleComparator);

    }

    protected int calculateSpeed(){
        return (this.speedMax <= (this.speedMax/ (this.vehicles.size() < 1 ? 1 : this.vehicles.size())) ?  this.speedMax : (this.speedMax/ (this.vehicles.size() < 1 ? 1 : this.vehicles.size())));
    }
    protected int calculateMarkdownFactor(int obstacles){
        return (obstacles == 0 ? 1 : 2);
    }
}
