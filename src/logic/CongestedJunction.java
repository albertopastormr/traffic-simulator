package logic;

import ini.IniSection;

import java.util.Comparator;

public class CongestedJunction extends GenericJunction<InRoadInterval> {
    public CongestedJunction(String id) {
        super(id);
    }


    @Override
    protected InRoadInterval createInRoad(Road road) {
        return new InRoadInterval(road, 0);
    }

    @Override
    public void completeSectionDetails(IniSection is) {
        is.setValue("type", "mc");
        super.completeSectionDetails(is);
    }

    @Override
    protected void updateTrafficLight() {
        if(this.greenTrafficLightIndex == -1){
            this.greenTrafficLightIndex =  this.indexOfMaxVehiclesqueueSize(-1);
            this.InRoads.get(this.greenTrafficLightIndex).setTrafficLight(true);
        }
        else{
            InRoadInterval actualGreenLightRoad = (InRoadInterval) this.InRoads.get(this.greenTrafficLightIndex);
            if(actualGreenLightRoad.consumedTime()){
                actualGreenLightRoad.setTrafficLight(false);
                actualGreenLightRoad.setFullUsage(false);
                actualGreenLightRoad.setTimeUnitsUsed(0);
                this.greenTrafficLightIndex = this.indexOfMaxVehiclesqueueSize(this.greenTrafficLightIndex);
                InRoadInterval nextGreenLightRoad = (InRoadInterval) this.InRoads.get(this.greenTrafficLightIndex);
                nextGreenLightRoad.setTrafficLight(true);
                nextGreenLightRoad.setTimeUnitsUsed(0);
                nextGreenLightRoad.setTimeInterval(Math.max(nextGreenLightRoad.vehiclesQueue.size()/2, 1));
            }
        }
    }
    private int indexOfMaxVehiclesqueueSize(int indexNotValid){
        int maxSize = 0, indexMaxQueueSize = -1;
        for (int i = 0; i < this.InRoads.size(); i++){
            if(this.InRoads.get(i).vehiclesQueue.size() > maxSize && i != indexNotValid){
                maxSize = this.InRoads.get(i).vehiclesQueue.size();
                indexMaxQueueSize = i;
            }
        }
        return indexMaxQueueSize;
    }
}
