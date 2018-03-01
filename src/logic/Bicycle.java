package logic;

import error.EventException;
import ini.IniSection;

import java.util.List;

public class Bicycle extends Vehicle {
    public Bicycle(String id, int speedMax, List<GenericJunction<?>> itinerary) throws EventException {
        super(id, speedMax, itinerary);
    }

    @Override
    public void setBreakdownTime(int breakdownTime) {
        if(this.speedActual >= this.speedMax / 2)
            super.setBreakdownTime(breakdownTime);
    }

    @Override
    public void completeSectionDetails(IniSection is) {
        is.setValue("type", "bike");
        super.completeSectionDetails(is);
    }
}
