package logic;

import ini.IniSection;

import java.util.List;
import java.util.Map;

 public class Junction extends SimulationObject {
    protected int GreenTrafficLightIndex; // lleva el índice de la road entrante // con el semáforo en verde

    protected List<InRoad> InRoads;
    // para optimizar las búsquedas de las roadsIns
    // (IdRoad, RoadIn)
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
        // devuelve la road que llega a ese junction desde “this”
        return new Road(null);
    }
    public void addRoadInToJunction(String idRoad, Road road) {
        // añade una road entrante al “mapaRoadsIns” y
        // a las “roadsIns”
    }
    public void addRoadOutAlJunction(Junction destino, Road road) {
        // añade una road saliente
    }
    public void inVehicleToJunction(String idRoad, Vehicle vehicle){
        // añade el “vehicle” a la road entrante “idRoad”
    }
    protected void updateTrafficLight(){
        // pone el semáforo de la road actual a “rojo”, y busca la siguiente
        // road entrante para ponerlo a “verde”
    }

}