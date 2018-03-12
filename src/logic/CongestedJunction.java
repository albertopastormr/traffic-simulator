package logic;

import error.EventException;
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
            InRoadInterval actualGreenLightRoad = this.InRoads.get(this.greenTrafficLightIndex);
            if(actualGreenLightRoad.consumedTime()){
                actualGreenLightRoad.setTrafficLight(false);
                actualGreenLightRoad.setFullUsage(false);
                actualGreenLightRoad.setTimeUnitsUsed(0);
                this.greenTrafficLightIndex = this.indexOfMaxVehiclesqueueSize(this.greenTrafficLightIndex);
                InRoadInterval nextGreenLightRoad =  this.InRoads.get(this.greenTrafficLightIndex);
                nextGreenLightRoad.setTrafficLight(true);
                nextGreenLightRoad.setTimeUnitsUsed(0);
                nextGreenLightRoad.setTimeInterval(Math.max(nextGreenLightRoad.vehiclesQueue.size()/2, 1));
            }
        }
    }

    @Override
    public void advance() throws EventException {
        if(!this.InRoads.isEmpty() && this.greenTrafficLightIndex == -1)
            this.InRoads.get(0).setTimeInterval(Math.max(this.InRoads.get(0).vehiclesQueue.size()/2, 1));
        super.advance();
    }

    private int indexOfMaxVehiclesqueueSize(int indexNotValid){
        if(this.InRoads.size() <= 1)
            return 0;
        int maxSize = -1, indexMaxQueueSize = -1;
        for (int i = 0; i < this.InRoads.size(); i++){
            if(this.InRoads.get(i).vehiclesQueue.size() > maxSize && i != indexNotValid){
                maxSize = this.InRoads.get(i).vehiclesQueue.size();
                indexMaxQueueSize = i;
            }
        }
        return indexMaxQueueSize;
    }
}
