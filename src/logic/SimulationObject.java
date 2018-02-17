package logic;

import ini.IniSection;

public abstract class SimulationObject {

    protected String id;

    public SimulationObject(String id) {
        // Por completar
    }

    public String getId() {
        return null;
    }

    @Override
    public String toString() {
        return null;
    }

    public String generateReport(int time) {
        IniSection is = new IniSection(this.getSectionName());
        is.setValue("id", this.id);
        is.setValue("time", time);
        this.completeSectionDetails(is);
        return is.toString();
    }

    public abstract void completeSectionDetails(IniSection is);
    public abstract String getSectionName();

    public abstract void advance();


}