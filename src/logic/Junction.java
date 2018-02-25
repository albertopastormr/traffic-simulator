package logic;

import error.EventException;
import ini.IniSection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

 public class Junction extends SimulationObject {
    protected int greenTrafficLightIndex; // It carries the index of the inRoad which has its traffic Light set to green

    protected List<InRoad> InRoads;

    protected Map<String, InRoad> mapInRoads;

    protected Map<Junction, Road> OutRoads;

    public Junction(String id) {
        super(id);
        this.InRoads = new ArrayList<InRoad>();
        this.mapInRoads = new HashMap<>();
        this.OutRoads = new HashMap<>();
        this.greenTrafficLightIndex = 0;
    }


     @Override
     public void completeSectionDetails(IniSection is) {
         is.setValue("queues", (this.InRoads.size() == 0 ? "" : this.InRoads ));
     }

     @Override
     public String getSectionName() {
         return "junction_report";
     }

     @Override
     public void advance() throws EventException {
        if(!this.InRoads.isEmpty()){
            this.InRoads.get(this.greenTrafficLightIndex).moveFirstVehicle();
            this.updateTrafficLight();
        }
     }

     public Road roadToJunction(Junction junction) {
        return this.OutRoads.get(junction);
    }
    public void addRoadInToJunction(String idRoad, Road road) throws EventException {
        if(!mapInRoads.containsKey(idRoad)) {
            InRoad inRoad = new InRoad(road);
            this.mapInRoads.put(idRoad, inRoad);
            this.InRoads.add(inRoad);
        }
        else
            throw new EventException("Road " + "already exists in mapInRoads\n");
    }
    public void addRoadOutToJunction(Junction destination, Road road) {
        OutRoads.put(destination, road);
    }
    public void inVehicleToJunction(String idRoad, Vehicle vehicle){
        mapInRoads.get(idRoad).addVehicleToQueue(vehicle);
    }
    protected void updateTrafficLight(){
        this.InRoads.get(this.greenTrafficLightIndex).setTrafficLight(false); // Setting actual traffic light index as red (false)
        this.greenTrafficLightIndex = (this.greenTrafficLightIndex == this.InRoads.size() ? 0 : this.greenTrafficLightIndex++); // Updating index circulating around InRoads
        this.InRoads.get( this.greenTrafficLightIndex ).setTrafficLight(true);
    }

}