package logic;

import ini.IniSection;

public class Freeway extends Road {

    protected int numLanes;

    public Freeway(String id, int length, int maxSpeed, GenericJunction<?> source, GenericJunction<?> destination, int numLanes) {
        super(id, length, maxSpeed, source, destination);
        this.numLanes = numLanes;
    }

    @Override
    public void completeSectionDetails(IniSection is) {
        is.setValue("type", "lanes");
        is.setValue("lanes", this.numLanes);
        super.completeSectionDetails(is);
    }

    @Override
    protected int calculateSpeed() {
        int newPossibleSpeed = (this.speedMax * this.numLanes/ (this.vehicles.size() < 1 ? 1 : this.vehicles.size())) + 1;
        return (this.speedMax <= newPossibleSpeed ?  this.speedMax : newPossibleSpeed);
    }

    @Override
    protected int calculateMarkdownFactor(int obstacles) {
        return ((obstacles < this.numLanes )? 1 : 2);
    }
}
