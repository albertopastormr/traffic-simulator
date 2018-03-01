package logic;

import error.EventException;
import ini.IniSection;

import java.util.List;
import java.util.Random;

public class Car extends Vehicle {

    protected int kmLastBreakdown;
    protected int kmResistance;
    protected int maxDurationBreakdown;
    protected double probabilityBreakdown;
    protected Random randomNum;

    public Car(String id, int speedMax, int kmResistance, double probabilityBreakdown, long seed, int maxDurationBreakdown, List<GenericJunction<?>> itinerary) throws EventException {
        super(id, speedMax, itinerary);
        this.kmResistance = kmResistance;
        this.probabilityBreakdown = probabilityBreakdown;
        this.randomNum = new Random(seed);
        this.maxDurationBreakdown = maxDurationBreakdown;
    }

    @Override
    public void advance() {
        if(this.breakdownTime > 0)
            this.kmLastBreakdown = this.kilometrage;
        else{
            if(this.kilometrage >= this.kmResistance && this.randomNum.nextDouble() < probabilityBreakdown)
                this.breakdownTime = this.randomNum.nextInt(this.maxDurationBreakdown) + 1;
        }
        super.advance();
    }

    @Override
    public void completeSectionDetails(IniSection is) {
        is.setValue("type", "car");
        is.setValue("resistance", this.kmResistance);
        is.setValue("fault_probability", this.probabilityBreakdown);
        is.setValue("max_fault_duration", this.maxDurationBreakdown);
        is.setValue("seed", this.randomNum);
        super.completeSectionDetails(is);
    }
}
