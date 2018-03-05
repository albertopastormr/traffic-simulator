package logic;


public class Junction extends GenericJunction<InRoad> {

     public Junction(String id) {
         super(id);
     }

   @Override
     protected InRoad createInRoad(Road road) {
         return new InRoad(road);
     }

     protected void updateTrafficLight(){
         this.InRoads.get(this.greenTrafficLightIndex).setTrafficLight(false); // Setting actual traffic light index as red (false)
         this.greenTrafficLightIndex = ((this.greenTrafficLightIndex + 1) % this.InRoads.size()); // Updating index circulating around InRoads
         this.InRoads.get( this.greenTrafficLightIndex ).setTrafficLight(true);
     }
 }