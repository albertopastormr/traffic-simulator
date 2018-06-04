package logic;

import error.EventException;
import ini.IniSection;

import java.util.List;

public class Tank extends Vehicle {

	private int numberOfMissiles;

	public Tank(String id, int speedMax, List<GenericJunction<?>> itinerary, int numberOfMissiles) throws EventException {
		super(id, speedMax, itinerary);
		this.numberOfMissiles = numberOfMissiles;
	}

	@Override
	public void advance() {
		if(this.breakdownTime == 0)
			this.speedActual = this.speedMax / ( numberOfMissiles > 0 ? numberOfMissiles + 1 : 1);
		super.advance();
	}

	@Override
	public void setBreakdownTime(int breakdownTime) {
		super.setBreakdownTime(breakdownTime * (numberOfMissiles/2));
	}

	@Override
	public void completeSectionDetails(IniSection is) {
		super.completeSectionDetails(is);
		is.setValue("type", "tank");
		is.setValue("number_missiles", numberOfMissiles);
	}
}
