package logic;

import error.EventException;
import ini.IniSection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

 public class Junction extends SimulationObject {
    protected int GreenTrafficLightIndex; // It carries the index of the inRoad which has its traffic Light set to green

    protected List<InRoad> InRoads;

    protected Map<String, InRoad> mapInRoads;

    protected Map<Junction, Road> OutRoads;

    public Junction(String id) {
        super(id);
        this.InRoads = new ArrayList<InRoad>();
        this.mapInRoads = new HashMap<>();
        this.OutRoads = new HashMap<>();
    }


     @Override
     public void completeSectionDetails(IniSection is) {

     }

     @Override
     public String getSectionName() {
         return null;
     }

     @Override
     public void advance() {

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
        mapInRoads.get(idRoad).vehiclesQueue.add(vehicle);
    }
    protected void updateTrafficLight(){
        InRoads.get(GreenTrafficLightIndex).setTrafficLight(false);
        InRoads.get(GreenTrafficLightIndex + 1).setTrafficLight(true);
        GreenTrafficLightIndex++;
    }

}