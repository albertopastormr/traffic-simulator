package logic;

import error.EventException;
import ini.IniSection;

public class TollRoad extends Road {

	private int toll_cost;
	private int toll_total;

	public TollRoad(String id, int length, int maxSpeed, GenericJunction<?> source, GenericJunction<?> destination, int toll_cost) {
		super(id, length, maxSpeed, source, destination);
		this.toll_cost = toll_cost;
		this.toll_total = 0;
	}

	@Override
	public void inVehicle(Vehicle vehicle) throws EventException {
		super.inVehicle(vehicle);
		toll_total += toll_cost;
	}

	@Override
	public void completeSectionDetails(IniSection is) {
		is.setValue("type", "toll");
		is.setValue("toll_cost", toll_cost);
		is.setValue("toll_total", toll_total);
		super.completeSectionDetails(is);
	}

	@Override
	protected int calculateSpeed() {
		return this.speedMax;
	}
}
