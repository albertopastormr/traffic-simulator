package logic;

import error.EventException;
import ini.IniSection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract public class GenericJunction<T extends InRoad> extends SimulationObject {

    protected int greenTrafficLightIndex; // It carries the index of the inRoad which has its traffic Light set to green

    protected List<InRoad> InRoads;

    protected Map<String, InRoad> mapInRoads;

    protected Map<GenericJunction<?>, Road> OutRoads;

    public GenericJunction(String id) {
        super(id);
        this.InRoads = new ArrayList<>();
        this.mapInRoads = new HashMap<>();
        this.OutRoads = new HashMap<>();
        this.greenTrafficLightIndex = -1;
    }


    @Override
    public void completeSectionDetails(IniSection is) {
        if(this.InRoads.size() == 0)
            is.setValue("queues",  "");
        else{
            String s = this.InRoads.get(0).toString();
            for(int i = 1; i < this.InRoads.size(); i++)
                s += "," + this.InRoads.get(i).toString();
            is.setValue("queues", s);
        }
    }

    @Override
    public String getSectionName() {
        return "junction_report";
    }

    @Override
    public void advance() throws EventException {
        if(!this.InRoads.isEmpty()){
            if(this.greenTrafficLightIndex == -1){
                this.greenTrafficLightIndex = 0;
                this.InRoads.get(this.greenTrafficLightIndex).setTrafficLight(true);
            }
            else {
                this.InRoads.get(this.greenTrafficLightIndex).moveFirstVehicle();
                this.updateTrafficLight();
            }
        }
    }

    public Road roadToJunction(GenericJunction<?> junction) throws EventException {
        if(this.OutRoads.containsKey(junction))
            return this.OutRoads.get(junction);
        else
            throw new EventException("There is not a road that connects junction " + this.id  + " to junction " + junction.id + "\n");
    }

    public void addRoadInToJunction(String idRoad, Road road) throws EventException {
        if(!mapInRoads.containsKey(idRoad)) {
            InRoad  inRoad = createInRoad(road);
            this.mapInRoads.put(idRoad, inRoad);
            this.InRoads.add(inRoad);
        }
        else
            throw new EventException("Road " + idRoad + " already exists in mapInRoads\n");
    }
    abstract protected T createInRoad(Road road);
    public void addRoadOutToJunction(GenericJunction<?> destination, Road road) {
        this.OutRoads.put(destination, road);
    }
    public void inVehicleToJunction(String idRoad, Vehicle vehicle){
        this.mapInRoads.get(idRoad).addVehicleToQueue(vehicle);
    }
    abstract protected void updateTrafficLight();

}