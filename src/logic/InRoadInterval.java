package logic;

import error.EventException;

public class InRoadInterval extends InRoad {

    private int timeInterval;
    private int timeUnitsUsed;
    private boolean fullUsage;
    private boolean usedByVehicle;

    public InRoadInterval(Road road, int timeInterval) {
        super(road);
        this.timeInterval = timeInterval;
        this.fullUsage = false;
        this.usedByVehicle = false;
        this.timeUnitsUsed = 0;
    }

    @Override
    void moveFirstVehicle() throws EventException {
        this.timeUnitsUsed++;
        if(this.vehiclesQueue.size() <= 0)
            this.fullUsage = false;
        else{
            this.vehiclesQueue.get(0).moveNextRoad();
            this.vehiclesQueue.remove(0);
            this.usedByVehicle = true;
        }
    }

    public boolean consumedTime(){
        return ((this.timeInterval - this.timeUnitsUsed) <= 0);
    }
    public boolean getFullUsage(){
        return this.fullUsage;
    }
    public boolean getUsedByVehicle(){
        return this.usedByVehicle;
    }

    public int getTimeInterval() {
        return this.timeInterval;
    }

    public void setTimeInterval(int timeInterval) {
        this.timeInterval = timeInterval;
    }

    public void setTimeUnitsUsed(int timeUnitsUsed) {
        this.timeUnitsUsed = timeUnitsUsed;
    }

    public void setFullUsage(boolean fullUsage) {
        this.fullUsage = fullUsage;
    }

    public void setUsedByVehicle(boolean usedByVehicle) {
        this.usedByVehicle = usedByVehicle;
    }

    @Override
    public String toString() {
        String s = "(" + this.road.getId() +","+ (this.trafficLight ? "green:" + String.valueOf(this.timeInterval - this.timeUnitsUsed) : "red") + ",";
        s += "[";
        if(this.vehiclesQueue.size() > 0) {
            s += this.vehiclesQueue.get(0).getId();
            for (int i = 1; i < this.vehiclesQueue.size(); i++) {
                s += "," + this.vehiclesQueue.get(i).getId();
            }
        }
        s += "])";
        return s;
    }
}
