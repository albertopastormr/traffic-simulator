package logic;

import ini.IniSection;

import java.util.List;
import java.util.Map;

 public class Junction extends SimulationObject {
    protected int GreenTrafficLightIndex; // It carries the index of the inRoad which has its traffic Light set to green

    protected List<InRoad> InRoads;

    protected Map<String, InRoad> mapInRoads;

    protected Map<Junction, Road> OutRoads;

    public Junction(String id) {
        super(id);
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
    public void addRoadInToJunction(String idRoad, Road road) {
        InRoad inRoad = new InRoad(road);
        this.mapInRoads.put(idRoad, inRoad);
        this.InRoads.add(inRoad);
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