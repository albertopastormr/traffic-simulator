package logic;

import ini.IniSection;

public class Roundabout extends GenericJunction<InRoadInterval> {
    protected int minValueInterval;
    protected int maxValueInterval;
    public Roundabout(String id, int minValueInterval, int maxValueInterval) {
        super(id);
        this.minValueInterval = minValueInterval;
        this.maxValueInterval = maxValueInterval;
    }

    @Override
    protected InRoadInterval createInRoad(Road road) {
        return new InRoadInterval(road, this.maxValueInterval);
    }
    @Override
    public void completeSectionDetails(IniSection is) {
        is.setValue("type", "rr");
        super.completeSectionDetails(is);
    }
    @Override
    protected void updateTrafficLight() {
        if(this.greenTrafficLightIndex == -1){
            this.greenTrafficLightIndex = 0;
            this.InRoads.get(this.greenTrafficLightIndex).setTrafficLight(true);
        }
        else{
            InRoadInterval actualGreenLightRoad = (InRoadInterval) this.InRoads.get(this.greenTrafficLightIndex);
            if(actualGreenLightRoad.consumedTime()){
                actualGreenLightRoad.setTrafficLight(false);

                if(actualGreenLightRoad.getFullUsage())
                    actualGreenLightRoad.setTimeInterval(Math.min(actualGreenLightRoad.getTimeInterval() + 1,this.maxValueInterval));
                else{
                    if(!actualGreenLightRoad.getUsedByVehicle())
                        actualGreenLightRoad.setTimeInterval(Math.max(actualGreenLightRoad.getTimeInterval() - 1, this.minValueInterval));
                }
                actualGreenLightRoad.setTimeUnitsUsed(0);
                actualGreenLightRoad.setUsedByVehicle(false);
                actualGreenLightRoad.setFullUsage(true);
                this.greenTrafficLightIndex = ((this.greenTrafficLightIndex + 1) % this.InRoads.size());
                InRoadInterval nextGreenLightRoad = (InRoadInterval) this.InRoads.get(this.greenTrafficLightIndex);
                nextGreenLightRoad.setTrafficLight(true);
                nextGreenLightRoad.setTimeUnitsUsed(0);
            }
        }
    }
}
