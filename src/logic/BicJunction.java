package logic;

import ini.IniSection;

public class BicJunction extends GenericJunction<InRoadInterval> {

	private int timeSlice;

	public BicJunction(String id, int timeSlice) {
		super(id);
		this.timeSlice = timeSlice;
	}

	@Override
	protected InRoadInterval createInRoad(Road road) {
		return new InRoadInterval(road, this.timeSlice);
	}

	@Override
	public void completeSectionDetails(IniSection is) {
		is.setValue("type", "bic");
		super.completeSectionDetails(is);
	}

	@Override
	protected void updateTrafficLight() {
		if(this.greenTrafficLightIndex == -1){
			this.greenTrafficLightIndex = indexMaxBicycleNumber(-1);
			this.InRoads.get(this.greenTrafficLightIndex).setTrafficLight(true);
		}
		else{
			InRoadInterval actualGreenLightRoad = (InRoadInterval) this.InRoads.get(this.greenTrafficLightIndex);
			if(actualGreenLightRoad.consumedTime()){
				actualGreenLightRoad.setTrafficLight(false);
				actualGreenLightRoad.setTimeUnitsUsed(0);
				actualGreenLightRoad.setUsedByVehicle(false);
				actualGreenLightRoad.setFullUsage(true);
				this.greenTrafficLightIndex = indexMaxBicycleNumber(this.greenTrafficLightIndex);
				InRoadInterval nextGreenLightRoad = (InRoadInterval) this.InRoads.get(this.greenTrafficLightIndex);
				nextGreenLightRoad.setTrafficLight(true);
				nextGreenLightRoad.setTimeUnitsUsed(0);
			}
		}
	}

	private int indexMaxBicycleNumber(int indexNotValid){
		if(this.InRoads.size() <= 1)
			return 0;
		int maxBicNumber = -1, indexMaxBicNumber = -1;
		for (int i = 0; i < this.InRoads.size(); i++){
			int numberOfBicyclesAtQueue = this.InRoads.get(i).numberOfBicyclesAtQueue();
			if(numberOfBicyclesAtQueue > maxBicNumber && i != indexNotValid){
				maxBicNumber = numberOfBicyclesAtQueue;
				indexMaxBicNumber = i;
			}
		}
		return indexMaxBicNumber;
	}
}
