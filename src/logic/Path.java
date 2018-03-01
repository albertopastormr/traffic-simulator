package logic;

import ini.IniSection;

public class Path extends Road {
    public Path(String id, int length, int maxSpeed, GenericJunction<?> source, GenericJunction<?> destination) {
        super(id, length, maxSpeed, source, destination);
    }

    @Override
    public void completeSectionDetails(IniSection is) {
        is.setValue("type", "dirt");
        super.completeSectionDetails(is);
    }

    @Override
    protected int calculateSpeed() {
        return this.speedMax;
    }

    @Override
    protected int calculateMarkdownFactor(int obstacles) {
        return obstacles + 1;
    }
}
